package com.wran.linkshort.controller;

import com.wran.linkshort.client.OAuthTokenLoginClient;
import com.wran.linkshort.model.TokenResponseDto;
import com.wran.linkshort.model.UserCreateDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Base64;

@RestController
public class TokenLoginController {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    private Logger LOGGER = LogManager.getLogger(getClass());

    @Autowired
    OAuthTokenLoginClient tokenLoginClient;

    @PostMapping("/login")
    public TokenResponseDto login(@RequestParam("username") String username,
                                  @RequestParam("password") String password){
        username = new String(Base64.getDecoder().decode(username.trim().getBytes()));
        password = new String(Base64.getDecoder().decode(password.trim().getBytes()));
        String authenticationHeader = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

        LOGGER.info("username: {} | password: {} | header: {}", username, password, authenticationHeader);
        return tokenLoginClient.getToken("Basic " + authenticationHeader,
                "password", username, password);
    }

    @PostMapping("/register")
    public TokenResponseDto register(@RequestBody UserCreateDto user){
        user.setUsername(new String(Base64.getDecoder().decode(user.getUsername().trim().getBytes())));
        user.setPassword(new String(Base64.getDecoder().decode(user.getPassword().trim().getBytes())));
        String authenticationHeader = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

        ResponseEntity response = tokenLoginClient.registerUser(user);
        if(response.getStatusCode() == HttpStatus.OK){
            LOGGER.info("User {} registered successfully", user.getUsername());
            return tokenLoginClient.getToken("Basic " + authenticationHeader,
                    "password", user.getUsername(), user.getPassword());
        }
        LOGGER.info("Registration failed for user {}", user.getUsername());
        return new TokenResponseDto();
    }
}
