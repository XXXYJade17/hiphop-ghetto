package com.xxxyjade.hiphopghetto.inteceptor;

import com.xxxyjade.hiphopghetto.enums.BaseCode;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameworkException;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "update",
        args = {Connection.class}
)})
public class AffectedRowsInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 执行原数据库操作，获取影响行数
        Object result = invocation.proceed();
        int affectedRows = (Integer) result;

        // 影响行数为0时，抛出异常
        if (affectedRows == 0) {
            throw new HipHopGhettoFrameworkException(BaseCode.DATABASE_EXCEPTION);
        }

        return result;
    }

    // 插件代理对象创建（固定写法）
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    // 读取配置参数（此处无需配置，留空即可）
    @Override
    public void setProperties(Properties properties) {}
}
