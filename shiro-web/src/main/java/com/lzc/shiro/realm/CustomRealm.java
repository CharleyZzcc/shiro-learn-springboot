package com.lzc.shiro.realm;

import com.lzc.shiro.entity.Permission;
import com.lzc.shiro.entity.Role;
import com.lzc.shiro.entity.User;
import com.lzc.shiro.service.PermissionService;
import com.lzc.shiro.service.RoleService;
import com.lzc.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义Realm
 * @author lzc
 * @date 2020/11/29 20:12
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    {
        // md5加密1次
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("md5");
        matcher.setHashIterations(1);
        super.setCredentialsMatcher(matcher);
    }

    /**
     * 授权信息
     * <p>
     *     如果不加缓存，每执行一次subject的权限相关方法，
     *     如hasRole，父类的hasRole就会执行一次该方法；
     *     如hasRoles,父类的hasRole和ModularRealmAuthorizer的hasRoles都会调用一次这个方法
     * </p>
     * @param principalCollection
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @author lzc
     * @date 2020/11/29 20:44
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取当前用户名
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userService.getByUsername(username);
        if (user == null) {
            return null;
        }
        // 获取用户角色
        List<Role> roles = roleService.findByUserId(user.getId());
        Set<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
        Set<String> roleNames = roles.stream().map(Role::getRoleName).collect(Collectors.toSet());
        // 获取用户角色权限
        List<Permission> permissionList = permissionService.findByRoleIds(roleIds);
        Set<String> permissions = permissionList.stream().map(Permission::getPermission).collect(Collectors.toSet());

        // 生成授权信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roleNames);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 认证信息
     * @param authenticationToken token
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @author lzc
     * @date 2020/11/29 20:12
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1.从主体传过来的认证信息中获取用户名
        String username = (String) authenticationToken.getPrincipal();
        User user = userService.getByUsername(username);
        if (user == null || user.getPassword() == null) {
            return null;
        }

        // 生成简单的待认证信息
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(), "CustomRealm");
        // 密码加盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        return authenticationInfo;
    }

    public static void main(String[] args) {
        // e10adc3949ba59abbe56e057f20f883e
        System.out.println(new Md5Hash("123456").toString());
        // 9bad41710724cf6511abde2a52416881
        System.out.println(new Md5Hash("123456", "zhangsan").toString());
        // 1b539b60601b934441308049a9526e7d
        System.out.println(new Md5Hash("123456", "lisi").toString());
    }
}
