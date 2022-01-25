package com.self.cloudserver.service;

import com.self.cloudserver.entity.Role;
import com.self.cloudserver.entity.User;
import com.self.cloudserver.security.token.JwtInfo;
import com.self.cloudserver.security.token.LoginUser;
import com.self.cloudserver.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Web用户认证
 */
@Service
public class WebUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(WebUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getUserByLoginName(s);
        if(user == null){
            logger.info("登陆用户 {} 不存在", s);
            throw new RuntimeException("登陆用户不存在");
        }else if(user.getEnAble() == 1){
            logger.info("登陆用户 {} 已禁用", s);
            throw new RuntimeException("登陆用户已禁用");
        }

        return buildLoginUser(user);
    }

    /**
     * 生成登陆账户身份
     * @param user 用户
     * @return 账户身份
     */
    public UserDetails buildLoginUser(User user){
        JwtInfo jwtInfo = new JwtInfo();
        BeanUtil.copyNullProperties(user, jwtInfo);

        List<String> authorityList = new ArrayList<>();

        //获取角色--TODO
        Role role = roleService.getRoleByUserId(user.getId());
        if(role != null){
            authorityList.add(role.getName());
        }

        //获取授权--TODO
        List<String> permissions = userService.getPermissionsByUserId(user.getId());
        authorityList.addAll(permissions);

        return new LoginUser(jwtInfo, authorityList);
    }

}
