package com.self.cloudserver.service;

import com.self.cloudserver.entity.Role;
import org.springframework.stereotype.Service;

/**
 * 角色
 */
@Service
public class RoleService {

    public Role getRoleByUserId(Long userId){
        //TODO--获取角色
        Role role = new Role();
        role.setId(1L);
        role.setName("普通用户");

        return role;
    }

}
