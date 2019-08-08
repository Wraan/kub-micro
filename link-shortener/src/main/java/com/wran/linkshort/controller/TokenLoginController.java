package com.wran.linkshort.controller;

import com.wran.linkshort.client.OAuthTokenLoginClient;
import com.wran.linkshort.model.TokenResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
public class TokenLoginController {

    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Autowired
    OAuthTokenLoginClient tokenLoginClient;

    @PostMapping("/login")
    public TokenResponseDto login(@RequestParam("username") String username,
                                  @RequestParam("password") String password){

        String header = clientId.trim() + ":" + clientSecret.trim();
        header = Base64.getEncoder().encodeToString(header.getBytes());

        return tokenLoginClient.getToken("Basic " + header,
                "password", username, password);
    }
}
