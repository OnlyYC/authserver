package com.liaoyb.security.web.controller;

import com.liaoyb.security.web.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @PostMapping("/regist")
    public void regist(UserForm user, HttpServletRequest request){
        //注册用户（保存到数据库，并返回用户id）
        String userId = user.getUsername();


        //绑定关系(userId 和第三方登录的用户id)
        providerSignInUtils.doPostSignUp(userId, new ServletWebRequest(request));
    }
}
