package com.self.cloudserver.webservice.impl;

import com.self.cloudserver.webservice.WebServiceTestService2;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@Service
@WebService(serviceName = "WebServiceTestService2", //与接口中指定的name一致
        targetNamespace = "http://webservice.cloudserver.self.com", //与接口中的命名空间一致,一般是接口的包名倒序
        endpointInterface = "com.self.cloudserver.webservice.WebServiceTestService2" //接口地址
)
public class WebServiceTestServiceImpl2 implements WebServiceTestService2 {

    @Override
    public String testWebService2(String param) {
        String prefix = "cloudtwo2:";
        return prefix + param;
    }

}
