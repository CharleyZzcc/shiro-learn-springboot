package com.lzc.shiro.dao;

import com.lzc.shiro.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lzc
 * @date 2020/11/29 20:52
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

}
