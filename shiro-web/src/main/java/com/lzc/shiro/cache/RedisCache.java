package com.lzc.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lzc
 * @date 2020/12/8 22:53
 * See More: https://github.com/charleyzzcc, https://gitee.com/charleyzz
 */
@Component
public class RedisCache<K, V> implements Cache<K, V> {

    @Resource
    private RedisTemplate<String, V> redisTemplate;

    private final String CACHE_PREFIX = "SHIRO-CACHE:";

    @Override
    public V get(K k) throws CacheException {
        System.out.println("从缓存获取权限数据");
        return redisTemplate.opsForValue().get(this.getKey(k));
    }

    @Override
    public V put(K k, V v) throws CacheException {
        redisTemplate.opsForValue().set(this.getKey(k), v, 30, TimeUnit.MINUTES);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        V v = redisTemplate.opsForValue().get(this.getKey(k));
        redisTemplate.delete(this.getKey(k));
        return v;
    }

    @Override
    public void clear() throws CacheException {
        // 不需要重写
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    private String getKey(K k) {
        return CACHE_PREFIX + k.toString();
    }
}
