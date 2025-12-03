package com.xxxyjade.hiphopghetto.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineConfig {

    private static final int MAXIMUM_SIZE = 10000;
    private static final long EXPIRE_AFTER_WRITE = 30;
    private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;

    @Bean
    public Cache<String, Object> caffeineCache() {
        return Caffeine.newBuilder()
                .maximumSize(MAXIMUM_SIZE)
                .expireAfterWrite(EXPIRE_AFTER_WRITE, TIME_UNIT)
                .recordStats()
                .build();
    }

}
