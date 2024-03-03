package com.ecom.productsale.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// JwtUtil class is used to generate Jwt token and parse it.

public class JwtTokenUtil {

    public static final long JWT_EXPIRATION = 864_000_000; // 有效期为10天
    public static final String JWT_SECRET = "123456603043";

    public static String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public static String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
    }

//    public static boolean validateToken(String token, UserDetails userDetails) {
    public static boolean validateToken(String token, UserDetails userDetails) {

        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private static boolean isTokenExpired(String token) {
        final Date expiration = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}

