package com.xxxyjade.hiphopghetto.cache.impl;

import com.xxxyjade.hiphopghetto.cache.CacheService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
@Slf4j
@AllArgsConstructor
public class ThreeLevelCacheServiceImpl implements CacheService {

    private final CaffeineCacheServiceImpl caffeineCacheService;
    private final RedisCacheServiceImpl redisCacheService;

    /**
     * 三级缓存
     * 1. 查caffeine缓存
     * 2. 防穿透：布隆过滤器判断
     * 3. 查redis缓存
     * 4. 查数据库
     * 5. 存入caffeine，redis
     *
     * @param key 缓存键
     * @param clazz 缓存值类型
     * @param dbLoader 数据库加载器
     * @param expireSeconds 过期时间
     * @param <T> 缓存值类型
     * @return 缓存值
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<?> clazz, Supplier<? extends T> dbLoader, long expireSeconds) {
        // 查caffeine缓存
        Object result = caffeineCacheService.get(key, Object.class);
        if (result != null) {
            return (T) clazz.cast(result);
        }

        // 查redis缓存
        result = redisCacheService.get(key, clazz);
        if (result != null) {
            T res = (T) clazz.cast(result);
            // 存入caffeine
            caffeineCacheService.put(key, res, expireSeconds);
            return res;
        }

        // 查数据库
        T res = dbLoader.get();
        if (res == null) {
            // 缓存空值（防穿透），短期过期
            redisCacheService.put(key, null, 60);
            return null;
        }

        // 存入caffeine，redis
        redisCacheService.put(key, res, expireSeconds);
        caffeineCacheService.put(key, res, expireSeconds);
        return res;
    }

    /**
     * 放入缓存
     * @param key 缓存key
     * @param value 缓存value
     * @param expireSeconds 过期时间，单位秒
     */
    public void put(String key, Object value, long expireSeconds) {
        caffeineCacheService.put(key, value, expireSeconds);
        redisCacheService.put(key, value, expireSeconds);
    }

    /**
     * 删除缓存
     * @param key 缓存key
     */
    public void delete(String key) {
        caffeineCacheService.delete(key);
        redisCacheService.delete(key);
    }

    /**
     * 根据前缀删除缓存
     * @param prefix 缓存前缀
     */
    public void deleteByPrefix(String prefix) {
        if (prefix.endsWith("*")) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        caffeineCacheService.deleteByPrefix(prefix);
        redisCacheService.deleteByPrefix(prefix);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, () -> null, 0);
    }

    /**
     * 判断缓存是否存在
     * @param key 缓存键
     * @return 缓存是否存在
     */
    public boolean exists(String key) {
        return caffeineCacheService.exists(key) || redisCacheService.exists(key);
    }

}
