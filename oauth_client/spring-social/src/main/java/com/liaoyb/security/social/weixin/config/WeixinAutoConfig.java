package com.liaoyb.security.social.weixin.config;

import com.liaoyb.security.properties.MySocialProperties;
import com.liaoyb.security.social.view.ConnectView;
import com.liaoyb.security.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * @author zhailiang
 */
@Configuration
@ConditionalOnProperty(prefix = "my.social.weixin", name = "app-id")
public class WeixinAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private MySocialProperties mySocialProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        MySocialProperties.WeixinProperties weixinProperties = mySocialProperties.getWeixin();
        return new WeixinConnectionFactory(weixinProperties.getProviderId(), weixinProperties.getAppId(), weixinProperties.getAppSecret());
    }

    /**
     * 自定义绑定成功视图
     *
     * @return
     */
    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView() {
        return new ConnectView();
    }
}
