package com.lzc.shiro.service.impl;

import com.lzc.shiro.dao.RoleRepository;
import com.lzc.shiro.dao.UserRoleRepository;
import com.lzc.shiro.entity.Role;
import com.lzc.shiro.entity.UserRole;
import com.lzc.shiro.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lzc
 * @date 2020/12/6 16:45
 * See More: https://github.com/charleyzzcc, https://gitee.com/charleyzz
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<Role> findByUserId(Integer userId) {
        Set<Integer> roleIds = userRoleRepository.findByUserId(userId).stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        return roleRepository.findAllById(roleIds);
    }
}
