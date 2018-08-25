package com.liaoyb.security.config;

import com.liaoyb.security.properties.MySocialProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MySocialProperties mySocialProperties;

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    /**
     * 1\这里记得设置requestMatchers,不拦截需要token验证的url
     * 不然会优先被这个filter拦截,走用户端的认证而不是token认证
     * 2\这里记得对oauth的url进行保护,正常是需要登录态才可以
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .antMatchers("/", "/home").authenticated()
                .antMatchers("/logout").permitAll()
                .antMatchers("/user/regist").permitAll()
                .antMatchers(mySocialProperties.getSignUpUrl()).permitAll()
                .and()
                .apply(springSocialConfigurer);

    }

    /**
     * springboot2.0 需密码使用的配置编码
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * support password grant type
     *
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}