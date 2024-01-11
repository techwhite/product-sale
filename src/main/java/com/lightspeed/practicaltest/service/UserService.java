package com.lightspeed.practicaltest.service;

import com.lightspeed.practicaltest.model.UserRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // todo: configure into file
    private final String userName = "andy-you";
    private String password = "mkfewfdk1343";

    public boolean checkUserNameAndPassword(UserRequest user) {
        // todo:
        // 1. check user's name and password with db data
        // 2. ACL and role check

        return userName.equals(user.getName()) && password.equals(user.getPassword());
    }
}
