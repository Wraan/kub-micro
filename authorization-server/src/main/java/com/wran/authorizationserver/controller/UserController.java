package com.wran.authorizationserver.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class UserController {

    @PostMapping("/test")
    public String test(@RequestParam("test") String test){
        return "Works: " + test;
    }
}
