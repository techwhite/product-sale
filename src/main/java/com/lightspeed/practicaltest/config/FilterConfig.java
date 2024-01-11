package com.lightspeed.practicaltest.config;

import com.lightspeed.practicaltest.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// The configuration is responsible for registering the authentication filter.

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean filter= new FilterRegistrationBean();
        filter.setFilter(new JwtFilter());

        // provide endpoints which needs to be restricted.
        // All Endpoints would be restricted if unspecified
        filter.addUrlPatterns("/products", "/products/*", "/sales", "/sales/*");

        return filter;
    }
}
