package com.self.cloudserver.api.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.self.cloudserver.security.token.JwtInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 登陆响应参数
 */
public class LoginResp {

    /**
     * token
     */
    private String token;

    /**
     * 用户基本信息
     */
    private JwtInfo jwtInfo;

    /**
     * 登陆用户id
     */
    private Long userId;

    /**
     * 所属组织id
     */
    private Long orgId;

    /**
     * 登陆时间
     */
    private Date loginTime;

    /**
     * 当前日期
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date curDate;

    /**
     * 过期时间(毫秒)
     */
    private Long expireTime;

    /**
     * 终端类型(web、app)
     */
    private String terminalType;

    /**
     * 访问入口(0-后台访问，1-前台访问)
     */
    private Integer accessEntrance;

    /**
     * 权限列表
     */
    private List<String> authorityList = new ArrayList<>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JwtInfo getJwtInfo() {
        return jwtInfo;
    }

    public void setJwtInfo(JwtInfo jwtInfo) {
        this.jwtInfo = jwtInfo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getCurDate() {
        return curDate;
    }

    public void setCurDate(Date curDate) {
        this.curDate = curDate;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public Integer getAccessEntrance() {
        return accessEntrance;
    }

    public void setAccessEntrance(Integer accessEntrance) {
        this.accessEntrance = accessEntrance;
    }

    public List<String> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<String> authorityList) {
        this.authorityList = authorityList;
    }

}
