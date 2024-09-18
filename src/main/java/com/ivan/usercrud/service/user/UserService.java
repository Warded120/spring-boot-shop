package com.ivan.usercrud.service.user;

import com.ivan.usercrud.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User save(User user);
    User findByUsername(String username);
}
