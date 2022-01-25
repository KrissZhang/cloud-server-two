package com.self.cloudserver.api.controller;

import com.self.cloudserver.api.req.LoginReq;
import com.self.cloudserver.api.resp.LoginResp;
import com.self.cloudserver.dto.ResultEntity;
import com.self.cloudserver.rpc.constants.RpcUri;
import com.self.cloudserver.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = RpcUri.LOGIN)
    public ResultEntity<LoginResp> login(HttpServletRequest request, @RequestBody LoginReq loginReq){
        return tokenService.login(request, loginReq);
    }

}
