package com.example.LoginSecurity.auth;

import java.util.Optional;

public interface ApplicationUserDAO {
    Optional<ApplicationUser> selectApplicationUserByUserName(String username);
}