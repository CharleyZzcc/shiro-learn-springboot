package com.lzc.shiro.dao;

import com.lzc.shiro.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * @author lzc
 * @date 2020/11/29 20:52
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer> {

    /**
     * 获取角色权限集合
     * @param roleIds 角色id集合
     * @return java.util.List&lt;com.lzc.shiro.entity.RolePermissions&gt;
     * @author lzc
     * @date 2020/11/29 20:54
     */
    List<RolePermission> findByRoleIdIn(Set<Integer> roleIds);
}
