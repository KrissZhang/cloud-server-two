package com.self.cloudserver.rpc;

import com.self.cloudserver.api.req.TestReq;
import com.self.cloudserver.constants.Insert;
import com.self.cloudserver.constants.Update;
import com.self.cloudserver.dto.ResultEntity;
import com.self.cloudserver.dto.RpcResponse;
import com.self.cloudserver.rpc.constants.RpcUri;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class RpcTestController {

    private static Logger logger = LoggerFactory.getLogger(RpcTestController.class);

    @GetMapping(RpcUri.TEST)
    public RpcResponse<Object> rpcTest(@RequestParam String req){
        return RpcResponse.ok(req);
    }

    @PostMapping(RpcUri.TEST_VALIDATE)
    public ResultEntity<TestReq> rpcTestValidate(@RequestBody @Validated(Insert.class) TestReq testReq){
        return ResultEntity.ok(testReq);
    }

    @PostMapping(RpcUri.TEST_VALIDATE2)
    public ResultEntity<TestReq> rpcTestValidate2(@RequestBody @Validated(Update.class) TestReq testReq){
        return ResultEntity.ok(testReq);
    }

}
