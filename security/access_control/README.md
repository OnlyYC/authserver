## 权限控制


### 权限表达式

需要同时满足多个授权：
使用access后面根据权限表达式的字符串形式
```
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/signin.html").permitAll()
                .antMatchers(HttpMethod.GET, "/user/*").access("hasRole('ADMIN') and hasIpAddress('127.0.0.1')")
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }
}
```