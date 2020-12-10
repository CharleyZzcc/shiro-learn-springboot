package com.lzc.shiro.config;

import com.lzc.shiro.cache.RedisCacheManager;
import com.lzc.shiro.realm.CustomRealm;
import com.lzc.shiro.session.CustomSessionManager;
import com.lzc.shiro.session.RedisSessionDAO;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author lzc
 * @date 2020/12/5 22:11
 */
@Configuration
public class ShiroConfig {

    @Bean
    public DefaultWebSecurityManager securityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(customRealm);
        // 为了调用方便，自定义的SessionManager、SessionDAO和CacheManager就在这边配置Bean
        // 使用自定义的session管理器
        securityManager.setSessionManager(this.sessionManager());
        // 使用自定义的缓存管理器，减少角色和权限的数据库请求
        securityManager.setCacheManager(this.cacheManager());
        // 使用自定义的cookie管理器
        securityManager.setRememberMeManager(this.cookieRememberMeManager());
        return securityManager;
    }

    /**
     * shiro过滤链定义（ShiroFilterFactoryBean自动装配了，具体配置见application.yml）
     * @return org.apache.shiro.spring.web.config.ShiroFilterChainDefinition
     * @author lzc
     * @date 2020/12/6 17:21
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        /*
         * shiro自带的过滤器在org.apache.shiro.web包下
         * 常用的有：
         *      anon        匿名访问，即不校验是否登录
         *      authc       登录后访问，不包括rememberMe
         *      user        登录后访问，包括rememberMe
         *      roles[xxx]  限定角色访问
         *      perms[xxx]  限定权限访问
         *      logout      指定退出地址，本工程不需要加，加了反而会（多次）重定向
         */
        // anon authc user roles[xxx] perms[xxxx]
        // 放行的在前，其余需要认证的放后面
        definition.addPathDefinition("/user/login", "anon");
        definition.addPathDefinition("/swagger-ui/**", "anon");
        definition.addPathDefinition("/swagger-resources/**", "anon");
        definition.addPathDefinition("/v3/**", "anon");
        definition.addPathDefinition("/**", "authc");
        // definition.addPathDefinition("/**", "user");
        return definition;
    }

    /**
     * 在Spring IoC容器中管理shiro的生命周期
     * @return org.apache.shiro.spring.LifecycleBeanPostProcessor
     * @author lzc
     * @date 2020/12/6 21:51
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 在IoC容器中使用shiro注解的代理
     * @return org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     * @author lzc
     * @date 2020/12/6 21:52
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 一定要设置为true才能使用
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 开启shiro注解
     * @param securityManager 安全管理器
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @author lzc
     * @date 2020/12/6 21:53
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager);
        return sourceAdvisor;
    }

    @Bean
    public SessionDAO sessionDAO() {
        return new RedisSessionDAO();
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new CustomSessionManager();
        sessionManager.setSessionDAO(this.sessionDAO());
        // 手动更改session的cookie名称，默认是JSESSIONID
        sessionManager.getSessionIdCookie().setName("TOKEN");

        // session cookie虚假的过期时间：默认-1即无过期，手动设置要加上8小时的时区时间，此处是东八区过期时间1小时。然而没有用
        // sessionManager.getSessionIdCookie().setMaxAge(32400);

        // session cookie真正的过期时间，设置东八区30秒后过期，在过期前请求接口，会重置服务器session的过期时间，session cookie过期之后会自动清除
        // sessionManager.setGlobalSessionTimeout(28_830L);
        return sessionManager;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        // RedisTemplate的key和value默认使用jdk自带的序列化方式，这里key改用string的
        redisTemplate.setKeySerializer(RedisSerializer.string());
        /*
         * value改用java()或byteArray()，这样RedisSessionDAO就可以注入RedisTemplate<String, Session>或RedisTemplate<String, byte[]>
         * 不能用json()，因为Session无法转换成json
         */
        redisTemplate.setValueSerializer(RedisSerializer.java());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.java());
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager() {
        return new RedisCacheManager();
    }

    /**
     * 配置cookie
     * @return org.apache.shiro.web.servlet.Cookie
     * @author lzc
     * @date 2020/12/8 23:17
     */
    @Bean
    public Cookie cookie() {
        SimpleCookie cookie = new SimpleCookie("REMEMBER_ME");
        // 3天 = 259200秒 = 60 * 60 * 24 * 3，但实际上要加上时区8小时（28800秒）才是东8区的3天；默认是一年
        cookie.setMaxAge(288000);
        return cookie;
    }

    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(this.cookie());
        return manager;
    }

}
