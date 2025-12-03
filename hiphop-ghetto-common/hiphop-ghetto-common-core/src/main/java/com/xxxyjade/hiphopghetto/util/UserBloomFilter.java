package com.xxxyjade.hiphopghetto.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
@Slf4j
public class UserBloomFilter {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // Redis中布隆过滤器的名称
    private static final String USER_BLOOM_FILTER = "bloom_filter::user";

    // 预估的用户数量
    private static final Long EXPECTED_INSERTIONS = 1000000L;

    // 误判率
    private static final Double ERROR_RATE = 0.01;

    private static final String RESERVE_COMMAND =  "BF.RESERVE";

    // Lua脚本
    private static final String INIT_SCRIPT = "redis.call('bf.reserve', KEYS[1], ARGV[1], ARGV[2])";
    private static final String ADD_SCRIPT = "return redis.call('bf.add', KEYS[1], ARGV[1])";
    private static final String EXIST_SCRIPT = "return redis.call('bf.exists', KEYS[1], ARGV[1])";

    /**
     * 初始化布隆过滤器
     */
    @PostConstruct
    public void init() {
        try{
            Boolean exists = redisTemplate.hasKey(USER_BLOOM_FILTER);
            if(Boolean.TRUE.equals(exists)) {
                log.info("布隆过滤器[{}]已存在", USER_BLOOM_FILTER);
                return;
            }
            Boolean init = redisTemplate.execute((RedisCallback<Boolean>) connection -> {
                // 获取原生Redis连接，执行BF.RESERVE命令
                byte[] keyBytes = redisTemplate.getStringSerializer().serialize(USER_BLOOM_FILTER);
                // BF.RESERVE 命令参数：key error_rate capacity
                Object[] params = new Object[]{ERROR_RATE, EXPECTED_INSERTIONS};
                // 执行原生命令（不同Redis客户端API略有差异，Lettuce下此方式更稳定）
                return connection.execute(RESERVE_COMMAND, keyBytes,
                        redisTemplate.getStringSerializer().serialize(params[0].toString()),
                        redisTemplate.getStringSerializer().serialize(params[1].toString())
                ) != null;
            });
            if (Boolean.FALSE.equals(init)) {
                log.error("布隆过滤器[{}]初始化失败", USER_BLOOM_FILTER);
            }
        } catch (Exception e) {
            log.error("布隆过滤器[{}]初始化异常", USER_BLOOM_FILTER, e);
        }
    }

    /**
     * 添加元素
     * @param arg 待添加的元素
     */
    @Transactional(rollbackFor = Exception.class)
    public void put(Object arg) {
        redisTemplate.execute(
                new DefaultRedisScript<>(ADD_SCRIPT, Boolean.class),
                Collections.singletonList(USER_BLOOM_FILTER),
                arg
        );
    }

    /**
     * 批量添加元素
     * @param args 待添加的元素
     */
    @Transactional(rollbackFor = Exception.class)
    public void put(Object... args) {
        for (Object arg : args) {
            put(arg);
        }
    }

    /**
     * 查看某个元素是否可能存在
     * @param arg 待查看的元素
     */
    public Boolean mightExist(Object arg) {
        return redisTemplate.execute(
                new DefaultRedisScript<>(EXIST_SCRIPT, Boolean.class),
                Collections.singletonList(USER_BLOOM_FILTER),
                arg
        );
    }

}
