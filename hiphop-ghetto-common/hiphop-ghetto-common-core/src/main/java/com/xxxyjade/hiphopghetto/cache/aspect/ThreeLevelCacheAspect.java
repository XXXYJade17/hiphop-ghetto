package com.xxxyjade.hiphopghetto.cache.aspect;

import com.xxxyjade.hiphopghetto.cache.annotation.ThreeLevelCache;
import com.xxxyjade.hiphopghetto.cache.impl.ThreeLevelCacheServiceImpl;
import com.xxxyjade.hiphopghetto.enums.BaseCode;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameworkException;
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

@Aspect
@Component
@AllArgsConstructor
@Slf4j
public class ThreeLevelCacheAspect {

    private final ThreeLevelCacheServiceImpl threeLevelCacheService;
    // SpEL表达式解析器
    private final ExpressionParser spelParser = new SpelExpressionParser();
    // 参数名解析器
    private final DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    /**
     * 切点：拦截所有加了@ThreeLevelCache的方法
     */
    @Around("@annotation(annotation)")
    public Object around(ProceedingJoinPoint joinPoint, ThreeLevelCache annotation) {
        // 获取方法信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> returnType = method.getReturnType();
        String[] paramNames = parameterNameDiscoverer.getParameterNames(method);
        Object[] args = joinPoint.getArgs(); // 方法入参

        String key = parseKey(
                annotation.key(),
                paramNames,
                args
        );

        // 过期时间转换（统一为秒）
        long expireSeconds = annotation.timeUnit().toSeconds(annotation.expire());

        return threeLevelCacheService.get(
                key,
                returnType,
                () -> {
                    // 执行原方法（业务层的DB查询逻辑）
                    try {
                        return joinPoint.proceed(args);
                    } catch (Throwable e) {
                        throw new HipHopGhettoFrameworkException(BaseCode.DATABASE_EXCEPTION);
                    }
                },
                expireSeconds
        );
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
