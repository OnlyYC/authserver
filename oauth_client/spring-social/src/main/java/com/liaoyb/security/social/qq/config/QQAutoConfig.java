package com.liaoyb.security.social.qq.config;

import com.liaoyb.security.properties.MySocialProperties;
import com.liaoyb.security.social.qq.connect.QQConnectionFactory;
import com.liaoyb.security.social.view.ConnectView;
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
@ConditionalOnProperty(prefix = "my.social.qq", name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private MySocialProperties mySocialProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        MySocialProperties.QQProperties qqConfig = mySocialProperties.getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }

    /**
     * 自定义绑定成功视图
     *
     * @return
     */
    @Bean({"connect/qqConnect", "connect/qqConnected"})
    @ConditionalOnMissingBean(name = "qqConnectedView")
    public View qqConnectedView() {
        return new ConnectView();
    }
}
