package com.wran.linkshort.client;

import com.wran.linkshort.model.TokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "auth-server", url = "${auth-server.client.url}", configuration = CustomFeignClientConfig.class)
@FeignClient(name = "auth-server", url = "${auth-server.client.url}")
public interface OAuthTokenLoginClient {

    @PostMapping("/public/test")
    String test(@RequestParam("test") String test);

    @PostMapping("/oauth/token")
    TokenResponseDto getToken(@RequestHeader("Authorization") String authorization,
                              @RequestParam("grant_type") String grantType,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password);
}
