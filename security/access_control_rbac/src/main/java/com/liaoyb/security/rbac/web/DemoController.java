package com.liaoyb.security.rbac.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liaoyb
 */
@RestController
public class DemoController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/user/{id}")
    public String user(@PathVariable("id") String id) {
        return "user" + id;
    }
}
