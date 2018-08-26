package com.liaoyb.security.rbac.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liaoyb
 */
public interface RbacService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
