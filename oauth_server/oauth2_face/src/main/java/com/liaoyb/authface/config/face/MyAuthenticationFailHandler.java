package com.liaoyb.authface.config.face;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liaoyb
 */
@Slf4j
@Component
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        log.info("登录失败");
//        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().write(objectMapper.writeValueAsString(exception.getLocalizedMessage()));

        super.onAuthenticationFailure(request, response, exception);
    }
}
