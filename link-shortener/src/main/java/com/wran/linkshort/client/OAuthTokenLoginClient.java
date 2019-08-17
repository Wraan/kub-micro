package com.wran.linkshort.client;

import com.wran.linkshort.model.TokenResponseDto;
import com.wran.linkshort.model.UserCreateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "auth-server", url = "${feign.client.auth-server.url}", configuration = CustomFeignClientConfig.class)
//@FeignClient(name = "auth-server", url = "${feign.client.auth-server.url}")
@FeignClient(name = "auth-server")
public interface OAuthTokenLoginClient {

    @PostMapping("/oauth/token")
    TokenResponseDto getToken(@RequestHeader("Authorization") String authorization,
                              @RequestParam("grant_type") String grantType,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password);

    @PostMapping("/oauth/user/register")
    ResponseEntity registerUser(UserCreateDto user);

}
