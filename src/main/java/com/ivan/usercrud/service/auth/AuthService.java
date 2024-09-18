package com.ivan.usercrud.service.auth;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    UserDetails getCurrentUser();
}
