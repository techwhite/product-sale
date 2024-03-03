package com.ecom.productsale.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
todo: investigate how to implement role authentication
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/products", "/products/*").permitAll()
                .antMatchers("/sales", "/sales/*").permitAll() // public access api endpoints
                .antMatchers("/api/login", "/api/register").permitAll()
                .anyRequest().authenticated() // need authenticated for other endpoints
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // add jwt authentication filter
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // disable session policy
    }

    /*
    Authenticate user/pw provided by request with authentication information pre-configured in memory.

    Can also define customized authentication logic
    todo: how to use
     */
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    /*
    configure user authentication information bases on memory, which is used in dev/test stages. In production, user
    info should be saved into db and write separated service class to interact with db
    todo: how to use?
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().getUserDetailsService()
////                .inMemoryAuthentication()
//                .withUser("user").password("{noop}password").roles("USER");
//    }

}


