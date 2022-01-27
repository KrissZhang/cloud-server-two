package com.self.cloudserver.rpc.constants;

public interface RpcUri {

    String RPC_PREFIX = "/rpc";

    String MOUDULE_URI_PREFIX = "/cloud/server/two";

    String TEST = RPC_PREFIX + MOUDULE_URI_PREFIX + "/test";

    String TEST_VALIDATE = RPC_PREFIX + MOUDULE_URI_PREFIX + "/testValidate";

    String TEST_VALIDATE2 = RPC_PREFIX + MOUDULE_URI_PREFIX + "/testValidate2";

    String LOGIN = "/api/token/login";

}
