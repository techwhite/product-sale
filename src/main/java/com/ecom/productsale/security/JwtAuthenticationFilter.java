package com.ecom.productsale.security;

import com.ecom.productsale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
Filter Chain is managed by Servlet layer, used to do some common and non-buz related logic (security, log, character
encode transformation, request or response conversion for request before coming to servlet or after.

The execution order of different filters can be decided by configuration order in the configuration files like
securityConfig in this project
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // skip jwt token authentication for login/register request
        if (request.getRequestURI().equals("/api/login") || request.getRequestURI().equals("/api/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        String username = null;
        String jwt = null;
        if (header != null && header.startsWith("Bearer ")) {
            jwt = header.substring(7);
            username = JwtTokenUtil.getUsernameFromToken(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // get username/password/role info from userservice and save into
            // SecurityContextHolder.getContext().setAuthentication for subsequent usage. e.g. role check
            UserDetails userDetails = userService.loadUserByUsername(username);
            if (JwtTokenUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}

