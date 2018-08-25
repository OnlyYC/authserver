package com.liaoyb.authface.config.face;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;

/**
 * @author liaoyb
 */
public class FaceAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //人脸识别凭证(1个凭证只能用于一次登录)(每次调用识别接口会生成多个识别凭证)

        FaceAuthenticationToken authenticationToken = (FaceAuthenticationToken) authentication;
        //验证凭证（识别对应分数达到阈值）

        //如果不通过，抛出异常
        if (StringUtils.isEmpty(authenticationToken.getPrincipal())) {
            throw new InternalAuthenticationServiceException("人脸识别凭证验证失败");
        }

        String username = "user2";

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
