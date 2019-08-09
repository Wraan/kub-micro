package com.wran.authorizationserver.service;

import com.wran.authorizationserver.model.oauth.User;
import com.wran.authorizationserver.model.dto.UserCreateDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MyUserDetailsService {

    UserDetails loadUserByUsername(String username);
    User register(UserCreateDto userCreateDto);
    List<User> saveAll(List<User> users);
}
