package com.lightspeed.practicaltest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// disable csrf by default

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling().disable();
//                .authorizeRequests()
//                .antMatchers("/products").permitAll()
//                .antMatchers("/products/**").permitAll()
//                .antMatchers("/sale").permitAll()
//                .antMatchers("/sale/*").permitAll()
//                .antMatchers("/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling().disable();
    }
}