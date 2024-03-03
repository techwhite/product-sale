package com.ecom.productsale.controller;

import com.ecom.productsale.model.User;
import com.ecom.productsale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
 provide user authorization and token generation abilities


 The whole lifecycle of user's is
 1. user register with name & password, optionally send confirmation link to user, user click it, then backend encode
 password and save username & pw into db/memory(test)
 2. user login with registered name and password, backend retrieve it from db and authenticate them; if successful,
 return generated token to user
 3. subsequent request should take token to backend server for validation
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/login")
    public ResponseEntity<Object> authenticate(@RequestBody User user) throws Exception {
        if (!checkParams(user)) {
            return ResponseEntity.badRequest().body("UserName or Password is Empty");
        }

        boolean res = userService.authenticate(user);

        String token = userService.getToken(user);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/api/register")
    public ResponseEntity<Object> register(@RequestBody User user) {
        if (!checkParams(user)) {
            return ResponseEntity.badRequest().body("UserName or Password is Empty");
        }

        boolean res = userService.register(user);

        return ResponseEntity.ok().body("register successfuly");
    }

    private boolean checkParams(User user) {
        if(!StringUtils.hasLength(user.getName()) || !StringUtils.hasLength(user.getPassword())) {
            return false;
        }

        return true;
    }
}
