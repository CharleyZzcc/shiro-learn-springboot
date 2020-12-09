package com.lzc.shiro.dao;

import com.lzc.shiro.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 角色dao
 * @author lzc
 * @date 2020/11/29 20:47
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
