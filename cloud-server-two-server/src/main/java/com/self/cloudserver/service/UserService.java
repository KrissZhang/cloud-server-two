package com.self.cloudserver.service;

import cn.hutool.crypto.SecureUtil;
import com.self.cloudserver.entity.User;
import com.self.cloudserver.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 */
@Service
public class UserService {

    public User getUserByLoginName(String loginName){
        //TODO--获取登陆用户
        User user = new User();
        user.setId(1L);
        user.setLoginName(loginName);
        String dbPwd = SecureUtil.md5("123");
        String enbcryptPwd = SecurityUtils.enbcryptPwd(dbPwd);
        user.setLoginPwd(enbcryptPwd);
        user.setUserName(loginName);
        user.setEnAble(0);

        return user;
    }

    public List<String> getPermissionsByUserId(Long userId){
        //TODO--获取指定用户权限
        return new ArrayList<>();
    }

}
