package com.xxxyjade.hiphopghetto.cache;

/**
 * 缓存顶层抽象接口
 */
public interface CacheService {

    /**
     * 放入缓存
     * @param key 缓存key
     * @param value 缓存value
     * @param expireSeconds 过期时间，单位秒
     */
    void put(String key, Object value, long expireSeconds);

    /**
     * 删除缓存
     * @param key 缓存key
     */
    void delete(String key);

    /**
     * 根据前缀删除缓存
     * @param prefix 缓存前缀
     */
    void deleteByPrefix(String prefix);

    /**
     * 获取缓存
     * @param key 缓存键
     * @param clazz 返回值类型
     * @param <T> 泛型
     * @return 缓存值
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * 判断缓存是否存在
     * @param key 缓存键
     * @return 是否存在
     */
    boolean exists(String key);

}
