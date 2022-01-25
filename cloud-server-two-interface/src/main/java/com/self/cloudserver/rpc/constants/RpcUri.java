package com.self.cloudserver.rpc.constants;

public interface RpcUri {

    String RPC_PREFIX = "/rpc";

    String MOUDULE_URI_PREFIX = "/cloud/server/two";

    String TEST = RPC_PREFIX + MOUDULE_URI_PREFIX + "/test";

    String LOGIN = "/api/token/login";

}
