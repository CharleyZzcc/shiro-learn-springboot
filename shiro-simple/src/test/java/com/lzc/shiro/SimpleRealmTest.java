package com.lzc.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * 简单的realm示例
 * @author lzc
 * @date 2020/11/29 16:41
 */
public class SimpleRealmTest {

    private final SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void initAccount() {
        simpleAccountRealm.addAccount("lzc", "123456", "admin", "user");
    }

    /**
     * 认证
     * @author lzc
     * @date 2020/11/29 16:50
     */
    @Test
    public void testAuthentication() {
        // 1.构建SecurityManager环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(simpleAccountRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        // 3.构建token
        UsernamePasswordToken token = new UsernamePasswordToken("lzc", "123456");

        // 4.认证
        System.out.println("是否认证：" + subject.isAuthenticated());
        subject.login(token);
        System.out.println("是否认证：" + subject.isAuthenticated());
        subject.logout();
        System.out.println("是否认证：" + subject.isAuthenticated());
    }

    /**
     * 授权
     * @author lzc
     * @date 2020/11/29 16:51
     */
    @Test
    public void testAuthorization() {
        // 1.构建SecurityManager环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(simpleAccountRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        // 3.构建token
        UsernamePasswordToken token = new UsernamePasswordToken("lzc", "123456");
        subject.login(token);

        // 4.校验角色
        if (subject.isAuthenticated()) {
            subject.checkRole("admin");
            subject.checkRoles("admin", "user");
        }
    }
}
