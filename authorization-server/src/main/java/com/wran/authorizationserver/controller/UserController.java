package com.wran.authorizationserver.controller;

import com.wran.authorizationserver.model.dto.UserCreateDto;
import com.wran.authorizationserver.model.oauth.User;
import com.wran.authorizationserver.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @PostMapping("/oauth/user/register")
    public ResponseEntity registerUser(@RequestBody UserCreateDto userDto){
        User user = userDetailsService.register(userDto);
        if(user != null)
            return new ResponseEntity(HttpStatus.OK);
        else
            return new ResponseEntity(HttpStatus.ALREADY_REPORTED);
    }

}
