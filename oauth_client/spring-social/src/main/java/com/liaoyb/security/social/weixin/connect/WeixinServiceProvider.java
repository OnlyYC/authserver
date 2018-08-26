package com.liaoyb.security.social.weixin.connect;

import com.liaoyb.security.social.weixin.api.Weixin;
import com.liaoyb.security.social.weixin.api.WeixinImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @author zhailiang
 */
public class WeixinServiceProvider extends AbstractOAuth2ServiceProvider<Weixin> {

    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";

    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";

    public WeixinServiceProvider(String appId, String appSecret) {
        super(new WeixinOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
    }

    @Override
    public Weixin getApi(String accessToken) {
        WeixinAccessGrant accessGrant = (WeixinAccessGrant) super.getOAuthOperations().refreshAccess(accessToken, "snsapi_login", null);
        return new WeixinImpl(accessGrant.getOpenid(), accessToken);
    }

}
