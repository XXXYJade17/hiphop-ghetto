package com.xxxyjade.hiphopghetto.cache.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target({ElementType.METHOD}) // 仅作用于方法
@Retention(RetentionPolicy.RUNTIME) // 运行时生效（AOP可解析）
@Documented
public @interface ThreeLevelCache {

    /**
     * 缓存键前缀（必填，如 "user::info::"）
     */
    String key();

    /**
     * 过期时间（默认30分钟）
     */
    long expire() default 1800;

    /**
     * 时间单位（默认秒）
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

}