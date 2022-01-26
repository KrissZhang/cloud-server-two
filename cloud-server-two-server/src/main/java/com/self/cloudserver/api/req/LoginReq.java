package com.self.cloudserver.api.req;

/**
 * 登录请求参数
 */
public class LoginReq {

    /**
     * 登陆名
     */
    private String loginName;

    /**
     * 登陆密码(小写md5加密)
     */
    private String loginPwd;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

}
