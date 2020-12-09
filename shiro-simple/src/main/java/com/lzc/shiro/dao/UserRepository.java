package com.lzc.shiro.dao;

import com.lzc.shiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 用户dao
 * @author lzc
 * @date 2020/11/29 20:15
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 获取用户
     * @param username 用户名
     * @return com.lzc.shiro.entity.Users
     * @author lzc
     * @date 2020/11/29 20:22
     */
    User getByUsername(String username);

}
