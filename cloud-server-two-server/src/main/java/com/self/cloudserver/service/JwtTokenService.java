package com.self.cloudserver.service;

import cn.hutool.core.lang.UUID;
import com.self.cloudserver.constants.CommonConstants;
import com.self.cloudserver.security.token.LoginUser;
import com.self.cloudserver.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken处理
 */
@Component
public class JwtTokenService {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    //令牌密钥
    private static final String SECRET_KEY = "abcdefghijklmnopqrstuvwxyz";

    //令牌过期时间(秒)
    private static final Long EXPIRE_SECONDS = 3600L;

    //刷新剩余时间(毫秒)
    private static final Long REFRESH_LEFT_MS = 600 * 1000L;

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 通过请求获取登陆账户
     * @param request request
     * @return 登陆账户
     */
    public LoginUser getLoginUserByReq(HttpServletRequest request){
        String token = getTokenByReq(request);
        return getLoginUserByToken(token);
    }

    /**
     * 通过token获取登陆账户
     * @param token token
     * @return 登陆账户
     */
    public LoginUser getLoginUserByToken(String token){
        if(StringUtils.isBlank(token)){
            return null;
        }

        LoginUser loginUser = null;
        try{
            //获取数据声明
            Claims claims = parseToken(token);

            //解析数据参数
            String tokenId = claims.get(CommonConstants.LOGIN_DETAIL_PREFIX).toString();
            String tokenKey = getCacheTokenKey(tokenId);

            loginUser = redisUtils.get(tokenKey);
        }catch (Exception e){
            logger.error("获取登陆账户失败", e);
        }

        return loginUser;
    }

    /**
     * 通过登陆账户创建token
     * @param loginUser 登陆账户
     * @return token
     */
    public String createTokenByLoginUser(LoginUser loginUser){
        String tokenId = UUID.fastUUID().toString(true);
        loginUser.setTokenId(tokenId);
        setToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(CommonConstants.LOGIN_DETAIL_PREFIX, tokenId);
        return createToken(claims);
    }

    /**
     * 刷新token
     * @param loginUser 登陆账户
     */
    public void refreshToken(LoginUser loginUser){
        long expireTime = loginUser.getExpireTime();
        long curTime = System.currentTimeMillis();
        if(REFRESH_LEFT_MS >= (expireTime - curTime)){
            setToken(loginUser);
        }
    }

    /**
     * 设置token
     * @param loginUser 登陆账户
     */
    public void setToken(LoginUser loginUser){
        loginUser.setLoginTime(new Date());
        loginUser.setExpireTime(loginUser.getLoginTime().getTime() + EXPIRE_SECONDS * 1000);

        //设置缓存
        String tokenKey = getCacheTokenKey(loginUser.getTokenId());
        redisUtils.set(tokenKey, loginUser, EXPIRE_SECONDS);
    }

    /**
     * 删除token
     * @param tokenId tokenId
     */
    public void deleteToken(String tokenId){
        if(StringUtils.isNotBlank(tokenId)){
            String tokenKey = getCacheTokenKey(tokenId);
            redisUtils.del(tokenKey);
        }
    }

    /**
     * 通过token获取登陆用户名
     * @param token token
     * @return 登陆用户名
     */
    public String getLoginNameByToken(String token){
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 从数据声明创建token
     * @param claims 数据声明
     * @return token
     */
    private String createToken(Map<String, Object> claims){
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    /**
     * 从token中获取数据声明
     * @param token token
     * @return 数据声明
     */
    private Claims parseToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * 获取token缓存key
     * @param tokenId tokenId
     * @return token缓存key
     */
    private static String getCacheTokenKey(String tokenId){
        return CommonConstants.LOGIN_DETAIL_PREFIX + tokenId;
    }

    /**
     * 获取请求token
     * @param request request
     * @return token
     */
    private String getTokenByReq(HttpServletRequest request){
        String token = request.getHeader(CommonConstants.TOKEN_HEADER_KEY);
        String terminalType = request.getHeader(CommonConstants.TOKEN_TERMINAL_TYPE);
        String tokenPrefix = getTokenPrefix(terminalType);
        if(StringUtils.isNotBlank(token) && token.startsWith(tokenPrefix)){
            token = token.replace(tokenPrefix, "");
        }

        return token;
    }

    /**
     * 根据设备类型获取token前缀
     * @param type 设备类型(web、app)
     * @return token前缀
     */
    public String getTokenPrefix(String type){
        if("app".equalsIgnoreCase(type)){
            return CommonConstants.TOKEN_APP_PREFIX;
        }

        return CommonConstants.TOKEN_WEB_PREFIX;
    }

}
