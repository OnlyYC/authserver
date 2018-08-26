package com.liaoyb.security.social.weixin.connect;

import com.liaoyb.security.social.weixin.api.Weixin;
import com.liaoyb.security.social.weixin.api.WeixinUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author zhailiang
 */
public class WeixinAdapter implements ApiAdapter<Weixin> {

    @Override
    public boolean test(Weixin api) {
        return true;
    }

    @Override
    public void setConnectionValues(Weixin api, ConnectionValues values) {
        WeixinUserInfo userInfo = api.getUserInfo();

        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getHeadimgurl());
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenid());
    }

    @Override
    public UserProfile fetchUserProfile(Weixin api) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateStatus(Weixin api, String message) {
        //do noting
    }

}
