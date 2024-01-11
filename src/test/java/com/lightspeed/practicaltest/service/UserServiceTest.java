package com.lightspeed.practicaltest.service;

import com.lightspeed.practicaltest.model.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void testCheckUserNameAndPassword() {
        UserRequest request = new UserRequest();
        request.setName("andy-you");
        request.setPassword("mkfewfdk1343");
        boolean res = userService.checkUserNameAndPassword(request);
        assertTrue(res);
    }
}
