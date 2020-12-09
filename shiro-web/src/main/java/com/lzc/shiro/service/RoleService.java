package com.lzc.shiro.service;

import com.lzc.shiro.entity.Role;

import java.util.List;

/**
 * @author lzc
 * @date 2020/12/6 16:45
 * See More: https://github.com/charleyzzcc, https://gitee.com/charleyzz
 */
public interface RoleService {

    /**
     * 获取用户角色名称
     * @param userId 用户id
     * @return java.util.Set&lt;java.lang.String&gt;
     * @author lzc
     * @date 2020/12/6 16:28
     */
    List<Role> findByUserId(Integer userId);
}
