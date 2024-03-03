package com.ecom.productsale.service;

import com.ecom.productsale.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {
    @Value("${user.authentication.password}")
    private String encodedPassword;

    @Autowired
    UserService userService;

    @Test
    public void testAuthenticate() {
        User request = new User();
        request.setName("andy-you");
        request.setPassword("mkfewfdk1343");
        try {
            boolean res = userService.authenticate(request);
            assertTrue(res);
        } catch (Exception e) {
            fail(e);
        }

        request.setPassword("mkfewfdk134");
        try {
            boolean res = userService.authenticate(request);
            assertFalse(res);
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testRegister() {
        User request = new User();
        request.setName("andy-you");
        request.setPassword("mkfewfdk1343");

        assertTrue(userService.register(request));

    }
}
