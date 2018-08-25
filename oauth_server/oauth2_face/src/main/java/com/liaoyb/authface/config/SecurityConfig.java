package com.liaoyb.authface.config;

import com.liaoyb.authface.config.face.FaceAuthenticationSecurityConfig;
import com.liaoyb.authface.config.face.MyAuthenticationFailHandler;
import com.liaoyb.authface.config.face.MyAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private FaceAuthenticationSecurityConfig faceAuthenticationSecurityConfig;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;

    /**
     * 1\这里记得设置requestMatchers,不拦截需要token验证的url
     * 不然会优先被这个filter拦截,走用户端的认证而不是token认证
     * 2\这里记得对oauth的url进行保护,正常是需要登录态才可以
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
//                .requestMatchers().antMatchers("/oauth/**","/login/**","/signin","/authentication","/logout/**") //表示security只匹配这些url(后面的配置都是在这些url基础上)
//                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
//                .antMatchers("/authentication","/authentication/face").authenticated() //登录表单需要security拦截"/oauth/**",
                .and()
                .formLogin()
                .loginPage("/signin").permitAll()
                .loginProcessingUrl("/authentication")
                .and().authorizeRequests().antMatchers("/error").permitAll()
//                .successHandler(myAuthenticationSuccessHandler) //成功处理器
//                .failureHandler(myAuthenticationFailHandler)  //失败处理器
                .and().exceptionHandling()
                .accessDeniedPage("/signin?authorization_error=true")
                .and().apply(faceAuthenticationSecurityConfig);
    }

    /**
     * springboot2.0 需密码使用的配置编码
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        String finalPassword = passwordEncoder().encode("123456");

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user1").password(finalPassword).authorities("USER").build());
        manager.createUser(User.withUsername("user2").password(finalPassword).authorities("USER").build());
        return manager;
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