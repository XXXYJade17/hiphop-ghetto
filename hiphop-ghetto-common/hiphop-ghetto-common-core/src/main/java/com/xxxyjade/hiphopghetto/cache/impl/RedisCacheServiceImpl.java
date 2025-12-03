package com.xxxyjade.hiphopghetto.cache.impl;

import com.xxxyjade.hiphopghetto.cache.CacheService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class RedisCacheServiceImpl implements CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 放入缓存
     * @param key 缓存key
     * value 缓存value
     * expireSeconds 过期时间，单位秒
     */
    public void put(String key, Object value, long expireSeconds) {
        if (key == null || value == null) return;
        redisTemplate.opsForValue().set(
                key,
                value,
                expireSeconds,
                TimeUnit.SECONDS
        );
    }

    /**
     * 删除缓存
     * @param key 缓存key
     */
    public void delete(String key) {
        if (key == null) return;
        redisTemplate.delete(key);
    }

    /**
     * 根据前缀删除缓存
     * @param prefix 缓存前缀
     */
    public void deleteByPrefix(String prefix) {
        if (prefix == null) return;
        Set<String> keys = redisTemplate.keys(prefix + "*");
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 获取缓存
     * @param key 缓存键
     * @param clazz 返回值类型
     * @return 缓存值
     * @param <T> 泛型
     */
    public <T> T get(String key, Class<T> clazz) {
        if (key == null) return null;
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? null : clazz.cast(value);
    }

    /**
     * 判断缓存是否存在
     * @param key 缓存键
     * @return 存在
     */
    public boolean exists(String key) {
        return key != null && redisTemplate.hasKey(key);
    }
}
