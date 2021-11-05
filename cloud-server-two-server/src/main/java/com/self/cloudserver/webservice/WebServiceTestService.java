package com.self.cloudserver.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface WebServiceTestService {

    @WebMethod
    String testWebService(@WebParam(name = "param") String param);

}
