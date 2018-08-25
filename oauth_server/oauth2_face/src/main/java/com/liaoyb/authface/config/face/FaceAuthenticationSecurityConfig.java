package com.liaoyb.authface.config.face;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * @author liaoyb
 */
@Component
public class FaceAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        FaceAuthenticationFilter faceAuthenticationFilter = new FaceAuthenticationFilter();
        faceAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
//        faceAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
//        faceAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);


        FaceAuthenticationProvider faceAuthenticationProvider = new FaceAuthenticationProvider();
        faceAuthenticationProvider.setUserDetailsService(userDetailsService);

        http.authenticationProvider(faceAuthenticationProvider)
                .addFilterAfter(faceAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        super.configure(http);
    }
}
