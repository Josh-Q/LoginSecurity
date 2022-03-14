package com.example.LoginSecurity.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


//    Override configure method with Basic Auth
    // problem with Basic Auth : cannot log out as user/pass will always be sent inside the request header
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // white listing some pages
                .antMatchers("/","index","/css/*","/js/*")
                .permitAll()
                // white listing some pages
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
//    Override configure method with Basic Auth



}