package com.xxxyjade.hiphopghetto.util;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类，封装常用操作
 */
@Component
@AllArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;


    /**
     * 设置永久缓存
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置（带过期时间）缓存
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 获取缓存
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除缓存
     */
    public Long delete(Collection<String> keys) {
        return redisTemplate.delete(keys);
    }

    /**
     * 判断缓存是否存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置缓存过期时间
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    // ============================ 批量操作（针对模糊匹配） ============================

    /**
     * 根据前缀模糊查询所有键
     */
    public Set<String> keys(String prefix) {
        return redisTemplate.keys(prefix + "*");
    }

    /**
     * 根据前缀批量删除缓存
     */
    public Long deleteByPrefix(String prefix) {
        Set<String> keys = keys(prefix);
        if (keys.isEmpty()) {
            return 0L;
        }
        return delete(keys);
    }

}