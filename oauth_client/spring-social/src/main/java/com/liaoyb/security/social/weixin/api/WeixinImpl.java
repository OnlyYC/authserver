/**
 *
 */
package com.liaoyb.security.social.weixin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @author zhailiang
 */
@Slf4j
public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin {


    private static final String URL_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";


    private String openId;
    private String accessToken;

    private ObjectMapper objectMapper = new ObjectMapper();

    public WeixinImpl(String openId, String accessToken) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.openId = openId;
        this.accessToken = accessToken;
    }

    /* (non-Javadoc)
     * @see com.imooc.security.core.social.qq.api.QQ#getUserInfo()
     */
    @Override
    public WeixinUserInfo getUserInfo() {

        String url = String.format(URL_GET_USERINFO, accessToken, openId);
        String result = getRestTemplate().getForObject(url, String.class);

        log.debug("微信用户信息:" + result);

        WeixinUserInfo userInfo = null;
        try {
            userInfo = objectMapper.readValue(result, WeixinUserInfo.class);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }

}
