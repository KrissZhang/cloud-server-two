package com.self.cloudserver.rpc.feign;

import com.self.cloudserver.dto.RpcResponse;
import com.self.cloudserver.rpc.constants.RpcUri;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface TestFeign {

    @GetMapping(RpcUri.TEST)
    RpcResponse<Object> rpcTest(@RequestParam String req);

}
