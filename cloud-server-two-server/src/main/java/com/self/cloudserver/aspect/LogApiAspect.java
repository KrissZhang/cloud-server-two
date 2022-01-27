package com.self.cloudserver.aspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogApiAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogApiAspect.class);

    /**
     * 切点
     */
    @Pointcut("execution(public * com.self.cloudserver.api.controller.*.*(..))")
    private void serviceAspect(){

    }

    @Before(value = "serviceAspect()")
    public void methodBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = null;
        HttpServletRequest request = null;
        logger.info("<<<<<<<<<<<<request api:{} content>>>>>>>>>>>>>>", joinPoint.getSignature().getName());
        try {
            requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(requestAttributes != null){
                request = requestAttributes.getRequest();
                logger.info("request address: {}", request.getRequestURL().toString());
                logger.info("request mode: {}", request.getMethod());
                logger.info("request method: {}", joinPoint.getSignature().getName());
                logger.info("request params: {}", joinPoint.getArgs());
            }
        }catch (Exception e){
            logger.error("###LogAspectServiceApi.class methodBefore() ### ERROR: ", e);
        }
    }

    @AfterReturning(returning = "retObj", pointcut = "serviceAspect()")
    public void methodAfterReturning(JoinPoint joinPoint, Object retObj){
        logger.info("====================response api:{}  content=====================", joinPoint.getSignature().getName());
        try {
            logger.info("reponse content: {}", JSON.toJSONString(retObj));
        }catch (Exception e){
            logger.error("###LogAspectServiceApi.class methodAfterReturning() ### ERROR: ", e);
        }
    }

    @AfterThrowing(throwing = "ex", pointcut = "serviceAspect()")
    public void methodAfterThrowing(JoinPoint joinPoint, Throwable ex){
        logger.info("the method {} is exception,the exception message: {}", joinPoint.getSignature().getName(), ex);
    }

}
