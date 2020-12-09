package com.lzc.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 内置文件的realm示例
 * @author lzc
 * @date 2020/11/29 19:28
 */
public class IniRealmTest {

    private final IniRealm iniRealm = new IniRealm("classpath:user.ini");

    @Test
    public void testAuthentication() {
        // 1.构建SecurityManager环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(iniRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        // 3.构建token
        UsernamePasswordToken token = new UsernamePasswordToken("lzc", "123456");

        // 4.校验角色权限
        subject.login(token);
        subject.checkRole("admin");
        subject.checkPermissions("user:select", "user:update");
    }
}
