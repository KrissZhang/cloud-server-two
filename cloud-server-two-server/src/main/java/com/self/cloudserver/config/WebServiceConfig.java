package com.self.cloudserver.config;

import com.self.cloudserver.webservice.WebServiceTestService;
import com.self.cloudserver.webservice.WebServiceTestService2;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {

    @Autowired
    private WebServiceTestService webServiceTestService;

    @Autowired
    private WebServiceTestService2 webServiceTestService2;

    /**
     * 注入servlet,bean name不能dispatcherServlet,否则会覆盖dispatcherServlet
     */
    @Bean(name = "cxfServlet")
    public ServletRegistrationBean cxfServlet() {
        //CXFServlet 注册地址(访问地址：http://ip：port/CXFServlet注册地址)
        return new ServletRegistrationBean(new CXFServlet(),"/webservice/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    /**
     * 注册WebServiceTestService接口到webservice服务
     */
    @Bean(name = "WebServiceTestEndpoint")
    public Endpoint sweptPayEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), webServiceTestService);

        //接口注册地址(wsdl文档地址：http://ip：port/CXFServlet注册地址/接口注册地址?wsdl)
        endpoint.publish("/webservice");

        return endpoint;
    }

    /**
     * 注册WebServiceTestService2接口到webservice服务
     */
    @Bean(name = "WebServiceTest2Endpoint")
    public Endpoint sweptPay2Endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), webServiceTestService2);

        //接口注册地址(wsdl文档地址：http://ip：port/CXFServlet注册地址/接口注册地址?wsdl)
        endpoint.publish("/webservice2");

        return endpoint;
    }

}
