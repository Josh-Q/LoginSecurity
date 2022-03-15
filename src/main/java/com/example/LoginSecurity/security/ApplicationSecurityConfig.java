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

import static com.example.LoginSecurity.security.ApplicationUserRole.*;

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
        http
                // csrf disabling
                .csrf().disable()
                // csrf disabling
                .authorizeRequests()
              // white listing some pages
                .antMatchers("/","index","/css/*","/js/*")
                .permitAll()
                // white listing some pages
                // access controlling some API for STUDENT role ( ROLE BASED AUTH )
                .antMatchers("/api/**")
                .hasRole(STUDENT.name())
                // access controlling some API for STUDENT role ( ROLE BASED AUTH )
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
        // Student user
        UserDetails player1 = User.builder()
                .username("player1")
                // apply the password encoder to the userDetailService
                .password(passwordEncoder.encode("password"))
                .roles(STUDENT.name())  // ROLE_STUDENT
                .build();

        // Admin user
        UserDetails lindaUser = User.builder()
                .username("Linda")
                .password(passwordEncoder.encode("password"))
                        .roles(ADMIN.name())
                        .build();


        // Admin Trainee user
        UserDetails tomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password"))
                .roles(ADMINTRAINEE.name())
                .build();

        return new InMemoryUserDetailsManager(
                player1,
                lindaUser,
                tomUser
        );
    }
    // how we retrieve user data from the database
}