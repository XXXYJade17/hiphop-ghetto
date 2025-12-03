package com.xxxyjade.hiphopghetto.cache.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD}) // 仅作用于方法
@Retention(RetentionPolicy.RUNTIME) // 运行时生效（AOP可解析）
@Documented
public @interface ThreeLevelCacheEvict {

    /**
     * 缓存键
     */
    String[] key() default {};

    /**
     * 缓存键前缀
     */
    String[] keyPrefix() default {};

    /**
     * 是否根据返回值决定删除缓存
     * true: 根据返回值决定是否删除（Boolean.TRUE时删除，其他情况不删除）
     * false: 总是删除缓存（默认行为）
     */
    boolean dependOnResult() default false;

}
