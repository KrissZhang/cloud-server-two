package com.self.cloudserver.service;

import com.self.cloudserver.api.req.LoginReq;
import com.self.cloudserver.api.resp.LoginResp;
import com.self.cloudserver.constants.CommonConstants;
import com.self.cloudserver.dto.ResultEntity;
import com.self.cloudserver.security.token.LoginUser;
import com.self.cloudserver.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Token处理
 */
@Service
public class TokenService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    public ResultEntity<LoginResp> login(HttpServletRequest request, LoginReq loginReq){
        //校验参数
        if(StringUtils.isBlank(loginReq.getLoginName()) || StringUtils.isBlank(loginReq.getLoginPwd())){
            throw new RuntimeException("用户名、密码不能为空");
        }

        UsernamePasswordAuthenticationToken uPwdAuthenticationToken = new UsernamePasswordAuthenticationToken(loginReq.getLoginName(), loginReq.getLoginPwd());
        //自动调用 loadUserByUsername 方法
        Authentication authentication = authenticationManager.authenticate(uPwdAuthenticationToken);
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        String tokenBody = jwtTokenService.createTokenByLoginUser(loginUser);
        String terminalType = request.getHeader(CommonConstants.TOKEN_TERMINAL_TYPE);
        String token = jwtTokenService.getTokenPrefix(terminalType) + tokenBody;

        LoginResp loginResp = new LoginResp();
        loginResp.setToken(token);
        BeanUtil.copyNullProperties(loginUser, loginResp);

        return ResultEntity.ok(loginResp);
    }

}
