package com.example.LoginSecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    // autowiring here autowires all of the services / variables
    @Autowired
    // autowiring here autowires all of the services / variables
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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


    // how we retrieve user data from the database
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails player1 = User.builder()
                .username("player1")
                // apply the password encoder to the userDetailService
                .password(passwordEncoder.encode("p@ssw0rd"))
                .roles("STUDENT")  // ROLE_STUDENT
                .build();

        return new InMemoryUserDetailsManager(
                player1
        );
    }
    // how we retrieve user data from the database
}