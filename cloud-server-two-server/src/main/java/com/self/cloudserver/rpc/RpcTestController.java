package com.self.cloudserver.rpc;

import com.self.cloudserver.dto.RpcResponse;
import com.self.cloudserver.rpc.constants.RpcUri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RpcTestController {

    private static Logger logger = LoggerFactory.getLogger(RpcTestController.class);

    @GetMapping(RpcUri.TEST)
    public RpcResponse<Object> rpcTest(@RequestParam String req){
        return RpcResponse.ok(req);
    }

}
