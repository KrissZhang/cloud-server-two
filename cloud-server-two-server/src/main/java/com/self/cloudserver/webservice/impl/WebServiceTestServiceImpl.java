package com.self.cloudserver.webservice.impl;

import com.self.cloudserver.webservice.WebServiceTestService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@Service
@WebService(serviceName = "WebServiceTestService", //与接口中指定的name一致
        targetNamespace = "http://webservice.cloudserver.self.com", //与接口中的命名空间一致,一般是接口的包名倒序
        endpointInterface = "com.self.cloudserver.webservice.WebServiceTestService" //接口地址
)
public class WebServiceTestServiceImpl implements WebServiceTestService {

    @Override
    public String testWebService(String param) {
        String prefix = "cloudtwo:";
        return prefix + param;
    }

}
