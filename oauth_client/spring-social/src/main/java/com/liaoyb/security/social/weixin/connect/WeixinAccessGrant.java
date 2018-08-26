package com.liaoyb.security.social.weixin.connect;

import lombok.Data;
import org.springframework.social.oauth2.AccessGrant;

/**
 * @author liaoyb
 */
@Data
public class WeixinAccessGrant extends AccessGrant {
    private String openid;

    public WeixinAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, String openid) {
        super(accessToken, scope, refreshToken, expiresIn);
        this.openid = openid;
    }
}
