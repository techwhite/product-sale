package com.ecom.productsale.controller;

import com.ecom.productsale.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    // Http mock tool
    @Autowired
    MockMvc mockMvc;

    // DI UserService since UserController depends on it.
    // Mock logic makes checkUserNameAndPassword() randomly return true or false
    @MockBean
    UserService service;

    @Test
    public void testLogin() throws Throwable {


    }
}
