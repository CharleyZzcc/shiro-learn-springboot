package com.lzc.shiro.dao;

import com.lzc.shiro.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 用户角色dao
 * @author lzc
 * @date 2020/11/29 20:47
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    /**
     * 获取用户角色集合
     * @param userId 用户id
     * @return java.util.List&lt;com.lzc.shiro.entity.UserRoles&gt;
     * @author lzc
     * @date 2020/11/29 20:48
     */
    List<UserRole> findByUserId(Integer userId);
}
