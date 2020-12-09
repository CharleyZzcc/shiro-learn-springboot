package com.lzc.shiro;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 简单和数据库交互的realm示例
 * @author lzc
 * @date 2020/11/29 19:40
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JdbcRealmTest {

    private final JdbcRealm jdbcRealm = new JdbcRealm();

    @Resource
    private DruidDataSource dataSource;

    @Before
    public void initDatasource() {
        jdbcRealm.setDataSource(dataSource);
        // 权限开关默认为false
        jdbcRealm.setPermissionsLookupEnabled(true);
    }

    @Test
    public void testDefaultSQL() {
        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        // 3.构建token
        UsernamePasswordToken token = new UsernamePasswordToken("lzc", "123456");

        // 4.认证与授权
        subject.login(token);
        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkRoles("admin","user");
        subject.checkPermission("user:select");
        subject.checkPermissions("user:select", "user:update");
    }

    @Test
    public void testCustomSQL() {
        // 自定义账户查询语句
        String authSql = "select password from user where username = ?";
        jdbcRealm.setAuthenticationQuery(authSql);
        // 自定义角色查询
        String roleSql = "select r.role_name from role r,user u,user_role ur where ur.user_id = u.id and ur.role_id = r.id and u.username = ?";
        jdbcRealm.setUserRolesQuery(roleSql);

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        // 3.构建token
        UsernamePasswordToken token = new UsernamePasswordToken("lzc", "123456");

        // 4.认证与授权
        subject.login(token);
        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkRoles("admin","user");
        subject.checkPermission("user:select");
        subject.checkPermissions("user:select", "user:update");
    }
}
