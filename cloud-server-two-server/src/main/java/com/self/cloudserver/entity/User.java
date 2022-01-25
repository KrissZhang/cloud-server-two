package com.self.cloudserver.entity;

public class User {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 登陆名
     */
    private String loginName;

    /**
     * 登陆密码
     */
    private transient String loginPwd;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 是否可用，0-可用，1-禁用
     */
    private Integer enAble;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getEnAble() {
        return enAble;
    }

    public void setEnAble(Integer enAble) {
        this.enAble = enAble;
    }

}
