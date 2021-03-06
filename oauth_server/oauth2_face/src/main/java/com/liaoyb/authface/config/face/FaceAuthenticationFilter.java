package com.liaoyb.authface.config.face;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liaoyb
 */
public class FaceAuthenticationFilter extends
        AbstractAuthenticationProcessingFilter {
    // ~ Static fields/initializers
    // =====================================================================================

    public static final String SPRING_SECURITY_FACE_CREDENTIALS_KEY = "faceCredentials";

    private String faceCredentialsParameter = SPRING_SECURITY_FACE_CREDENTIALS_KEY;
    private boolean postOnly = true;

    // ~ Constructors
    // ===================================================================================================

    public FaceAuthenticationFilter() {
        super(new AntPathRequestMatcher("/authentication/face", "POST"));
    }

    // ~ Methods
    // ========================================================================================================

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String faceCredentials = obtainFaceCredentials(request);

        if (faceCredentials == null) {
            faceCredentials = "";
        }


        FaceAuthenticationToken authRequest = new FaceAuthenticationToken(
                faceCredentials);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     * @return the username that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    protected String obtainFaceCredentials(HttpServletRequest request) {
        return request.getParameter(faceCredentialsParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request     that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     *                    set
     */
    protected void setDetails(HttpServletRequest request,
                              FaceAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login
     * request.
     *
     * @param faceCredentialsParameter the parameter name. Defaults to "username".
     */
    public void setFaceCredentialsParameter(String faceCredentialsParameter) {
        Assert.hasText(faceCredentialsParameter, "Username parameter must not be empty or null");
        this.faceCredentialsParameter = faceCredentialsParameter;
    }


    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getFaceCredentialsParameter() {
        return faceCredentialsParameter;
    }
}
