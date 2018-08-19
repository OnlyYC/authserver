package com.liaoyb.facelogin.security.face;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author liaoyb
 */
public class FaceAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //人脸识别凭证(1个凭证只能用于一次登录)(每次调用识别接口会生成一个凭证)


        //验证凭证（识别对应分数达到阈值）

        String username = "gg";

        //验证通过,获取用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);


        FaceAuthenticationToken authenticationResult = new FaceAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(userDetails);


        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FaceAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
