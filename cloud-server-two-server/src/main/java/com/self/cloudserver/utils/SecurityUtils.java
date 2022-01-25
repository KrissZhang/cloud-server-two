package com.self.cloudserver.utils;

import com.self.cloudserver.security.token.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 安全服务
 */
public class SecurityUtils {

    /**
     * 获取认证用户id
     * @return 认证用户id
     */
    public static Long getUserId() {
        try {
            return getLoginUser().getUserId();
        } catch (Exception e) {
            throw new RuntimeException("获取用户id异常");
        }
    }

    /**
     * 获取认证登陆名
     * @return 登陆名
     */
    public static String getLoginName() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new RuntimeException("获取用户账户异常");
        }
    }

    /**
     * 获取认证用户
     * @return 认证用户
     */
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new RuntimeException("获取认证用户异常");
        }
    }

    /**
     * 获取身份认证
     * @return Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 判断密码是否相同
     * @param rawPwd 真实密码
     * @param encodedPwd 加密后字符
     * @return true-相同，false-不相同
     */
    public static boolean checkPwdEquals(String rawPwd, String encodedPwd) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPwd, encodedPwd);
    }

    /**
     * BCryptPasswordEncoder 加密密码
     * @param rawPwd 真实密码
     * @return 加密密码
     */
    public static String enbcryptPwd(String rawPwd){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(rawPwd);
    }

    /**
     * 是否为超级管理员
     * @param userId 用户id
     * @return true-是超级管理员，false-不是超级管理员
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

}
