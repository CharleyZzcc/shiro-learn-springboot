package com.lzc.shiro.service.impl;

import com.lzc.shiro.dao.UserRepository;
import com.lzc.shiro.entity.User;
import com.lzc.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lzc
 * @date 2020/12/6 16:26
 * See More: https://github.com/charleyzzcc, https://gitee.com/charleyzz
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

}
