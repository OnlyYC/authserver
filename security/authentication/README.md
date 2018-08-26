## 身份认证




## security配置
### 记住我功能
**方案1：**
配置rememberMe()


```
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 @Override
    protected void configure(HttpSecurity http) throws Exception {
    http
        .rememberMe().tokenValiditySeconds(60*20)
        .anyRequest().authenticated();
        }
}
```
以上配置会注入TokenBasedRememberMeServices作为RememberMeServices实现。
会在cookie中保存名为remember-me的值，其值为 用户名、失效时间、签名通过base64加密后的值。


**方案2：**
配置tokenRepository，注入PersistentTokenRepository实现（如JdbcTokenRepositoryImpl（需要创建表））


此时会创建PersistentTokenBasedRememberMeServices作为RememberMeServices的实现
`注：`cookie中存放的值不会包含用户信息，更安全

配置：
```
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 @Override
    protected void configure(HttpSecurity http) throws Exception {
    http
        //记住我配置
        .rememberMe().tokenRepository(persistentTokenRepository())
        .anyRequest().authenticated();
        }


    /**
     * 记住我功能的token存取器配置
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }
}
```


spring源码：
RememberMeConfigurer
```
private AbstractRememberMeServices createRememberMeServices(H http, String key)
			throws Exception {
		return this.tokenRepository == null
				? createTokenBasedRememberMeServices(http, key)
				: createPersistentRememberMeServices(http, key);
	}
```


PersistentTokenBasedRememberMeServices
```
protected void onLoginSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication successfulAuthentication) {
		String username = successfulAuthentication.getName();

		logger.debug("Creating new persistent login for user " + username);

		PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(
				username, generateSeriesData(), generateTokenData(), new Date());
		try {
			tokenRepository.createNewToken(persistentToken);
			addCookie(persistentToken, request, response);
		}
		catch (Exception e) {
			logger.error("Failed to save persistent token ", e);
		}
	}
```



