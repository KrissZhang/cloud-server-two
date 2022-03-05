package com.self.cloudserver.aspect;

import com.self.cloudserver.annotation.RateLimiter;
import com.self.cloudserver.enums.LimitTypeEnum;
import com.self.cloudserver.exception.RateLimiterException;
import com.self.cloudserver.utils.IpUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 限流处理
 */
@Aspect
@Component
public class RateLimiterAspect {

    private static final Logger logger = LoggerFactory.getLogger(RateLimiterAspect.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private RedisScript<Long> limitScript;

    @Autowired
    public void setLimitScript(RedisScript<Long> limitScript) {
        this.limitScript = limitScript;
    }

    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) throws Throwable {
        String key = rateLimiter.key();
        int time = rateLimiter.time();
        int count = rateLimiter.count();

        String combineKey = getCombineKey(rateLimiter, point);
        List<String> keys = Collections.singletonList(combineKey);
        try {
            Long number = redisTemplate.execute(limitScript, keys, count, time);
            if (Objects.isNull(number) || number.intValue() > count) {
                throw new RateLimiterException("访问过于频繁，请稍候再试");
            }
            logger.debug("限制请求'{}',当前请求'{}',缓存key'{}'", count, number.intValue(), key);
        } catch (RateLimiterException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("服务器限流异常，请稍候再试");
        }
    }

    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        StringBuffer buffer = new StringBuffer(rateLimiter.key());
        if (rateLimiter.limitType() == LimitTypeEnum.IP) {
            buffer.append(IpUtils.getIpAddr(getRequest())).append("-");
        }
        HttpServletRequest request = getRequest();
        buffer.append(request.getRequestURI());
        return buffer.toString();
    }

    public HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes != null){
            return requestAttributes.getRequest();
        }

        return null;
    }

}
