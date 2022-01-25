package com.self.cloudserver.security.auth;

import com.self.cloudserver.security.token.WebAuthenticationToken;
import com.self.cloudserver.service.WebUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * web认证器
 */
@Component
public class WebAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private WebUserDetailsService webUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginName = (String) authentication.getPrincipal();
        UserDetails userDetails = webUserDetailsService.loadUserByUsername(loginName);

        //构建AuthenticationToken
        WebAuthenticationToken webAuthenticationToken = new WebAuthenticationToken(userDetails, userDetails.getAuthorities());
        webAuthenticationToken.setDetails(authentication.getDetails());

        return webAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(WebAuthenticationToken.class);
    }

}
