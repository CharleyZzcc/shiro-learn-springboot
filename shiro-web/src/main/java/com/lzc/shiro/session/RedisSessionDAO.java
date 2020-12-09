package com.lzc.shiro.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 自定义基于redis的SessionDAO
 * @author lzc
 * @date 2020/12/7 22:03
 * See More: https://github.com/charleyzzcc, https://gitee.com/charleyzz
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    @Resource
    private RedisTemplate<String, Session> redisTemplate;

    private final String SESSION_PREFIX = "SHIRO-SESSION:";

    @Override
    protected Serializable doCreate(Session session) {
        // 构建sessionId
        Serializable sessionId = super.generateSessionId(session);
        // 绑定sessionId
        super.assignSessionId(session, sessionId);
        // 保存session
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
        // 读取session
        String key = this.getKey(sessionId.toString());
        Session value = redisTemplate.opsForValue().get(key);
        return value;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session != null && session.getId() != null) {
            String key = this.getKey(session.getId().toString());
            redisTemplate.delete(key);
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        // 获取有效的session
        Set<String> keys = redisTemplate.keys(SESSION_PREFIX);
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        }
        Set<Session> sessions = new HashSet<>();
        for (String key : keys) {
            Session value = redisTemplate.opsForValue().get(key);
            if (value == null) {
                continue;
            }
            sessions.add(value);
        }
        return sessions;
    }

    private void saveSession(Session session) {
        if (session != null && session.getId() != null) {
            String key = this.getKey(session.getId().toString());
            // 设置服务端session过期时间为半小时
            redisTemplate.opsForValue().set(key, session, 30, TimeUnit.MINUTES);
        }
    }

    private String getKey(String sessionId) {
        return SESSION_PREFIX + sessionId;
    }
}
