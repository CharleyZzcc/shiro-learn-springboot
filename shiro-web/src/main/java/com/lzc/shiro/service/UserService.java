package com.lzc.shiro.service;

import com.lzc.shiro.entity.User;

/**
 * 用户服务
 * @author lzc
 * @date 2020/12/6 16:24
 * See More: https://github.com/charleyzzcc, https://gitee.com/charleyzz
 */
public interface UserService {

    /**
     * 获取用户
     * @param username 用户名
     * @return com.lzc.shiro.entity.User
     * @author lzc
     * @date 2020/12/6 16:25
     */
    User getByUsername(String username);

}
