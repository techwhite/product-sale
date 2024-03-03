package com.ecom.productsale.service;

import com.ecom.productsale.model.User;
import com.ecom.productsale.repository.UserRepository;
import com.ecom.productsale.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/*
By implement UserDetailsService's loadUserByUsername, you can define where you get user info, db/memory/*
 */
@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
//
//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                    new ArrayList<>());
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with exception happend. " + e.getMessage());
        }

    }

    /*
    used for login
     */
    public boolean authenticate(User user) throws Exception{
        // 1. check user's name and password with db data
        UserDetails userDetails = loadUserByUsername(user.getName());

        // 2. compare password
        return pwEncoder.matches(user.getPassword(), userDetails.getPassword());
    }

    public String getToken(User user) {
        UserDetails userDetails = loadUserByUsername(user.getName());

        return JwtTokenUtil.generateToken(userDetails);
    }

    public boolean register(User user) {
        try {
            String encryptedPassword = pwEncoder.encode(user.getPassword());
            logger.debug("rawPw:{} encoded:{}", user.getPassword(), encryptedPassword);

            // save into db
            return userRepository.saveRegisterUser(user.getName(), encryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("failed to register", e);
        }
    }

}
