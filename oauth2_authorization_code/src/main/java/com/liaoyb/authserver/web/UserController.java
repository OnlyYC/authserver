package com.liaoyb.authserver.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {


    @GetMapping("/userRedis")
    public Object getCurrentUserRedis(Authentication authentication) {
//        log.info("【SecurityOauth2Application】 getCurrentUserRedis authentication={}", JsonUtil.toJson(authentication));


        return authentication;
    }
}
