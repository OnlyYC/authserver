package com.liaoyb.security.config;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 如果授权过来的用户没有绑定过，自动给用户注册一个账号
 */
@Component
public class MyConnectionSignUp implements ConnectionSignUp {
    @Override
    public String execute(Connection<?> connection) {

        //todo 根据社交用户信息默认创建用户并返回用户唯一标识
        return UUID.randomUUID().toString();
    }
}
