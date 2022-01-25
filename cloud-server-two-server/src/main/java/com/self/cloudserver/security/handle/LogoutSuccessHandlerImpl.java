package com.self.cloudserver.security.handle;

import com.alibaba.fastjson.JSON;
import com.self.cloudserver.dto.ResultEntity;
import com.self.cloudserver.service.JwtTokenService;
import com.self.cloudserver.security.token.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 退出处理
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //删除用户token
        LoginUser loginUser = jwtTokenService.getLoginUserByReq(httpServletRequest);
        if(loginUser != null){
            jwtTokenService.deleteToken(loginUser.getTokenId());
        }

        httpServletResponse.setStatus(200);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.getWriter().print(JSON.toJSONString(ResultEntity.addError("200", "退出成功")));
    }

}
