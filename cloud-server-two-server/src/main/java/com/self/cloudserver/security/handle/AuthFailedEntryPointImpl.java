package com.self.cloudserver.security.handle;

import com.alibaba.fastjson.JSON;
import com.self.cloudserver.dto.ResultEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * 认证失败处理
 */
@Component
public class AuthFailedEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 5557318192871882892L;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(200);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        httpServletResponse.getWriter().print(JSON.toJSONString(ResultEntity.addError("402", "用户认证失败")));
    }

}
