package com.liaoyb.security.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "my.social")
public class MySocialProperties {
    /**
     * 社交登录，如果需要用户注册，跳转的页面
     */
    private String signUpUrl = "/signUp.html";
    /**
     * 社交登录功能拦截的url
     */
    private String filterProcessesUrl = "/auth";
    private QQProperties qq = new QQProperties();
    private WeixinProperties weixin = new WeixinProperties();

    @Data
    public static class QQProperties extends SocialProperties {
        /**
         * 第三方id，用来决定发起第三方登录的url，默认是 qq。
         */
        private String providerId = "qq";
    }

    @Data
    public static class WeixinProperties extends SocialProperties {
        /**
         * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
         */
        private String providerId = "weixin";
    }
}
