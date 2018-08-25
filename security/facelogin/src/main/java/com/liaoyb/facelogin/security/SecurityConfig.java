package com.liaoyb.facelogin.security;

import com.liaoyb.facelogin.security.face.FaceAuthenticationSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author liaoyb
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FaceAuthenticationSecurityConfig faceAuthenticationSecurityConfig;

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/signin.html")
                .loginProcessingUrl("/authentication")
                .successHandler(myAuthenticationSuccessHandler) //成功处理器
                .failureHandler(myAuthenticationFailHandler)  //失败处理器
                .and()
                .authorizeRequests()
                .antMatchers("/signin.html").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .apply(faceAuthenticationSecurityConfig);
    }
}
