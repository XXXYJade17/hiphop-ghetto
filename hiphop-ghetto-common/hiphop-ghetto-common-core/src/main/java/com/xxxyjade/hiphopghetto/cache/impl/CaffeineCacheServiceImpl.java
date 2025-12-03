package com.xxxyjade.hiphopghetto.cache.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.xxxyjade.hiphopghetto.cache.CacheService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CaffeineCacheServiceImpl implements CacheService {

    private final Cache<String, Object> caffeineCache;

    /**
     * 放入缓存
     * @param key 缓存key
     * @param value 缓存value
     * @param expireSeconds 过期时间，单位秒
     */
    public void put(String key, Object value, long expireSeconds) {
        if (key == null) return;
        caffeineCache.put(key, value);
    }

    /**
     * 删除缓存
     * @param key 缓存key
     */
    public void delete(String key) {
        if (key == null) return;
        caffeineCache.invalidate(key);

    }

    /**
     * 根据前缀删除缓存
     * @param prefix 缓存前缀
     */
    public void deleteByPrefix(String prefix) {
        if (prefix == null) return;
        caffeineCache.asMap().keySet().removeIf(key -> key.startsWith(prefix));
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
        Object value = caffeineCache.getIfPresent(key);
        return value == null ? null : clazz.cast(value);
    }


    /**
     * 判断缓存是否存在
     * @param key 缓存键
     * @return 存在
     */
    public boolean exists(String key) {
        return key != null && caffeineCache.getIfPresent(key) != null;
    }
}
