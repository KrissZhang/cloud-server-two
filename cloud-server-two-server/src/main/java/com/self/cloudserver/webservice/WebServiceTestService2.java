package com.self.cloudserver.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface WebServiceTestService2 {

    @WebMethod
    String testWebService2(@WebParam(name = "param") String param);

}
