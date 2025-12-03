package com.xxxyjade.hiphopghetto.cache.aspect;

import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCacheEvict;
import com.xxxyjade.hiphopghetto.cache.impl.ThreeLevelCacheServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class ThreeLevelCacheEvictAspect {

    private final ThreeLevelCacheServiceImpl threeLevelCacheService;
    // SpEL表达式解析器
    private final ExpressionParser spelParser = new SpelExpressionParser();
    // 参数名解析器
    private final DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * 切点：拦截所有加了@ThreeLevelCacheEvict的方法
     */
    @Around("@annotation(annotation)")
    public Object around(ProceedingJoinPoint joinPoint, ThreeLevelCacheEvict annotation) throws Throwable {
        // 获取方法信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] paramNames = parameterNameDiscoverer.getParameterNames(method);
        Object[] args = joinPoint.getArgs(); // 方法入参

        Set<String> keys = parseKeys(annotation, paramNames, args);

        try {
            // 先执行目标方法
            Object result = joinPoint.proceed(args);

            if (annotation.dependOnResult() && result instanceof Boolean res && Boolean.FALSE.equals(res)) {
                    return result;

            }

            if (!keys.isEmpty()) {
                for (String key : keys) {
                    if (key.endsWith("*")) {
                        // 前缀删除
                        String prefix = key.substring(0, key.length() - 1);
                        threeLevelCacheService.deleteByPrefix(prefix);
                    } else {
                        // 精确删除
                        threeLevelCacheService.delete(key);
                    }
                }
            }
            return result;
        } catch (Throwable e) {
            log.error("执行带缓存删除注解的方法出错", e);
            throw e;
        }
    }

    private Set<String> parseKeys(ThreeLevelCacheEvict annotation, String[] paramNames, Object[] args) {
        Set<String> keys = new HashSet<>();

        // 解析多个key
        for (String key : annotation.key()) {
            if (!key.isEmpty()) {
                keys.add(parseKey(key, paramNames, args));
            }
        }

        // 解析多个key前缀
        for (String prefix : annotation.keyPrefix()) {
            if (!prefix.isEmpty()) {
                keys.add(parseKey(prefix, paramNames, args) + "*");
            }
        }

        return keys;
    }

    private String parseKey(String key, String[] paramNames, Object[] args) {
        // 构建SpEL上下文：绑定方法参数 + 支持静态类调用
        StandardEvaluationContext context = new StandardEvaluationContext();

        if (paramNames != null && paramNames.length > 0) {
            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
        }
        // 兼容 #args[0] 按索引取参数
        context.setVariable("args", args);

        // 解析SpEL表达式
        Expression expression = spelParser.parseExpression(key);
        Object keyObj = expression.getValue(context);
        if (keyObj == null) {
            throw new RuntimeException("SpEL表达式解析缓存键为空：" + key);
        }
        return keyObj.toString();
    }

}
