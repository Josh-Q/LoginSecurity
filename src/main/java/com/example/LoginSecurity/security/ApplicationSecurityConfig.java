package com.example.LoginSecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.example.LoginSecurity.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
// Access Control
@EnableGlobalMethodSecurity(prePostEnabled = true)
// Access Control
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    // autowiring here autowires all of the services / variables
    @Autowired
    // autowiring here autowires all of the services / variables
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

//    ------------------------------BASIC AUTH -----------------------------------
    //    Override configure method with Basic Auth
    // problem with Basic Auth : cannot log out as user/pass will always be sent inside the request header
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//        http
//                // CSRF generation while preventing frontend JS manipulation
////                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
////                .and()
//                // CSRF generation while preventing frontend JS manipulation
//
//                // disable csrf for convenience for now
//                .csrf().disable()
//                // disable csrf for convenience for now
//                .authorizeRequests()
//              // white listing some pages
//                .antMatchers("/","index","/css/*","/js/*")
//                .permitAll()
//                // white listing some pages
//                // access controlling some API for STUDENT role ( ROLE BASED AUTH )
//                .antMatchers("/api/**")
//                .hasRole(STUDENT.name())
//                // access controlling some API for STUDENT role ( ROLE BASED AUTH )
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//    }
//    Override configure method with Basic Auth
//    ------------------------------BASIC AUTH -----------------------------------


    //    ------------------------------FORM BASED AUTH -----------------------------------
//        Override configure method with FORM BASED AUTH
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*")
                .permitAll()
               .antMatchers("/api/**")
                .hasRole(STUDENT.name())
              .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                // force redirect to page after successful log
                .defaultSuccessUrl("/courses", true);
                // force redirect to page after successful log
    }
//        Override configure method with FORM BASED AUTH






    //    ------------------------------FORM BASED AUTH ------------------------------------












    // how we retrieve user data from the database
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        // Student user
        UserDetails player1 = User.builder()
                .username("player1")
                // apply the password encoder to the userDetailService
                .password(passwordEncoder.encode("password"))
//                .roles(STUDENT.name())  // ROLE_STUDENT
                //
                .authorities(STUDENT.getGrantedAuthorities())
                //
                .build();

        // Admin user
        UserDetails lindaUser = User.builder()
                .username("Linda")
                .password(passwordEncoder.encode("password"))
//                        .roles(ADMIN.name()) // ROLE_ADMIN
                .authorities(ADMIN.getGrantedAuthorities())
                        .build();


        // Admin Trainee user
        UserDetails tomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMINTRAINEE.name())  // ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                player1,
                lindaUser,
                tomUser
        );
    }
    // how we retrieve user data from the database
}