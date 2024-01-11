package com.lightspeed.practicaltest.util;

import com.lightspeed.practicaltest.model.UserRequest;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

@SpringBootTest
public class JwtUtilTest {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Test
    public void testJwtUtil() {
        String name = "andy.you";
        UserRequest user = new UserRequest();
        user.setName(name);

        Map<String, String> obs = JwtUtil.generateToken(user);
        String token = obs.get("token");

        logger.debug("latest token: " + token);
        assertTrue(StringUtils.isNotBlank(obs.get("token")));

        Claims claims = JwtUtil.parseToken(token);
        assertEquals(name, claims.getSubject(), "parsed name is wrong!");
    }
}