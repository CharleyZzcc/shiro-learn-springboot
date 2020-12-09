package com.lzc.shiro;

import com.lzc.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 自定义的realm示例
 * @author lzc
 * @date 2020/11/29 20:31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomRealmTest {

    @Autowired
    private CustomRealm customRealm;

    @Test
    public void test() {
        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        // 3.构建token
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "123456");

        // 4.认证与授权
        subject.login(token);
        System.out.println("isAuthenticated:" + subject.isAuthenticated());
        subject.checkRole("admin");
        subject.checkPermissions("user:select", "user:update");
    }
}
