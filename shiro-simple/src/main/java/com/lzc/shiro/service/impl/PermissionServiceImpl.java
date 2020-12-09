package com.lzc.shiro.service.impl;

import com.lzc.shiro.dao.PermissionRepository;
import com.lzc.shiro.dao.RolePermissionRepository;
import com.lzc.shiro.entity.Permission;
import com.lzc.shiro.entity.RolePermission;
import com.lzc.shiro.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lzc
 * @date 2020/12/6 16:50
 * See More: https://github.com/charleyzzcc, https://gitee.com/charleyzz
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public List<Permission> findByRoleIds(Set<Integer> roleIds) {
        Set<Integer> permissionIds = rolePermissionRepository.findByRoleIdIn(roleIds).stream().map(RolePermission::getPermissionId).collect(Collectors.toSet());
        return permissionRepository.findAllById(permissionIds);
    }
}
