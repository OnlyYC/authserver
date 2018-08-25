package com.liaoyb.authface.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping
@Controller
public class DemoController {
    /**
     * 登录页面
     *
     * @return
     */
    @RequestMapping("/signin")
    public String signin() {
        return "signin";
    }


    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = {"/", "/home"})
    public String home(@AuthenticationPrincipal UserDetails userDetails, Map map) {
        //获取当前登录客户信息
        map.put("username", userDetails.getUsername());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "home";
    }

}
