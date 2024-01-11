package com.lightspeed.practicaltest.controller;

import com.lightspeed.practicaltest.model.UserRequest;
import com.lightspeed.practicaltest.service.UserService;
import com.lightspeed.practicaltest.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// provide user authrization and token generation abilities

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest user) {
        try {
            if(!StringUtils.hasLength(user.getName()) || !StringUtils.hasLength(user.getPassword())) {
                return ResponseEntity.badRequest().body("UserName or Password is Empty");
            }

            boolean res = userService.checkUserNameAndPassword(user);
            if (!res) {
                return ResponseEntity.badRequest().body("Invalid userName or Password");
            }

            return ResponseEntity.ok(JwtUtil.generateToken(user));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
