package com.liaoyb.app.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Controller
public class AppDemoController {
    public static final String SAVED_LOGIN_ORIGIN_URI =  "_SAVED_ORIGIN";
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/oauth/face/login")
    public String oauthLogin(HttpServletRequest request){
        //保存Refer到session中
        String referrer = request.getHeader("referer");
        if (!StringUtils.isEmpty(referrer) &&
                request.getSession().getAttribute(SAVED_LOGIN_ORIGIN_URI) == null) {
            log.debug("Saving login origin URI: {}", referrer);
            request.getSession().setAttribute(SAVED_LOGIN_ORIGIN_URI, referrer);
        }

        //重定向到授权页面
        return "redirect:http://localhost:8080/oauth/authorize?response_type=code&client_id=demoApp&redirect_uri=http://localhost:8081/auth/face/redirect";
    }

    @RequestMapping("/auth/face/redirect")
    public String getToken(@RequestParam String code, HttpServletRequest request, Map<String, String> map){
        log.info("receive code {}",code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("code",code);
        params.add("client_id","demoApp");
        params.add("client_secret","demoAppSecret");
        params.add("redirect_uri","http://localhost:8081/auth/face/redirect");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        //获取token
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/oauth/token", requestEntity, String.class);
        String token = response.getBody();
        log.info("token => {}",token);
        //返回页面（页面然后跳转到之前的页面）
        map.put("pageUrl", determineTargetUrl(request));

        return "refresh";
    }


    private String determineTargetUrl(HttpServletRequest request) {
        Object savedReferrer = request.getSession().getAttribute(SAVED_LOGIN_ORIGIN_URI);
        if (savedReferrer != null) {
            String savedLoginOrigin = request.getSession().getAttribute(SAVED_LOGIN_ORIGIN_URI).toString();
            log.debug("Redirecting to saved login origin URI: {}", savedLoginOrigin);
            request.getSession().removeAttribute(SAVED_LOGIN_ORIGIN_URI);
            return savedLoginOrigin;
        } else {
            return "/";
        }
    }

}
