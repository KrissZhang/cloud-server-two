package com.self.cloudserver.security.token;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * 登陆账户信息
 */
@JsonIgnoreProperties({"enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "authorities"})
public class LoginUser implements UserDetails {

    private static final long serialVersionUID = -9119790497151537462L;

    /**
     * 用户基本信息
     */
    private JwtInfo jwtInfo;

    /**
     * tokenId
     */
    private String tokenId;

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

    public LoginUser() {
        super();
    }

    public LoginUser(JwtInfo jwtInfo, List<String> authorityList) {
        this.userId = jwtInfo.getId();
        this.jwtInfo = jwtInfo;
        this.authorityList = authorityList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (String authority : authorityList) {
            authorities.add(new SimpleGrantedAuthority(authority));
        }
        return Collections.unmodifiableSet(authorities);
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public String getPassword() {
        return this.jwtInfo.getLoginPwd();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.jwtInfo.getLoginName();
    }

    /**
     * 账户是否过期(过期账户无法验证)
     * @return true-未过期，false-过期
     */
    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否锁定(锁定账户无法验证)
     * @return true-未锁定，false-锁定
     */
    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 账户凭据(密码)是否已过期，凭据过期无法验证
     * @return true-未锁定，false-锁定
     */
    @Override
    @JSONField(serialize = false)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否已禁用，禁用用户无法验证
     * @return true-可用，false-禁用
     */
    @Override
    @JSONField(serialize = false)
    public boolean isEnabled() {
        return (this.jwtInfo.getEnAble() == 0);
    }

    public JwtInfo getJwtInfo() {
        return jwtInfo;
    }

    public void setJwtInfo(JwtInfo jwtInfo) {
        this.jwtInfo = jwtInfo;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
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
