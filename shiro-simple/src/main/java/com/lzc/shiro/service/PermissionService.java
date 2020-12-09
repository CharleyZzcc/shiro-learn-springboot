package com.lzc.shiro.service;

import com.lzc.shiro.entity.Permission;

import java.util.List;
import java.util.Set;

/**
 * @author lzc
 * @date 2020/12/6 16:48
 * See More: https://github.com/charleyzzcc, https://gitee.com/charleyzz
 */
public interface PermissionService {

    /**
     * 获取权限
     * @param roleIds 角色id集合
     * @return java.util.List&lt;com.lzc.shiro.entity.Permission&gt;
     * @author lzc
     * @date 2020/12/6 16:49
     */
    List<Permission> findByRoleIds(Set<Integer> roleIds);
}
