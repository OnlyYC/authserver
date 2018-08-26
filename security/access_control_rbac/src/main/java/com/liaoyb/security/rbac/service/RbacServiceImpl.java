package com.liaoyb.security.rbac.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @author liaoyb
 */
@Service("rbacService")
public class RbacServiceImpl implements RbacService {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        //根据用户拥有权限的url，判断是否有权限访问

        Object principal = authentication.getPrincipal();

        boolean hasPermission = false;
        //读取用户拥有权限的url
        Set<String> urls = new HashSet<>();
        if(principal instanceof UserDetails){
            //todo
            if(StringUtils.equals(((UserDetails) principal).getUsername(), "user1")){
                hasPermission = true;
            }else {
                for (String url : urls) {
                    if (antPathMatcher.match(url, request.getRequestURI())) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }

        return hasPermission;
    }
}
