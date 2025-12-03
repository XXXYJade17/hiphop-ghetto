package com.xxxyjade.hiphopghetto.exception.handler;

import com.xxxyjade.hiphopghetto.enums.BaseCode;
import com.xxxyjade.hiphopghetto.exception.HipHopGhettoFrameworkException;
import com.xxxyjade.hiphopghetto.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HipHopGhettoFrameworkException.class)
    public Result<Void> handleHipHopGhettoFrameWorkException(HipHopGhettoFrameworkException e) {
        log.error("业务异常: ", e);
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理其他未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {
        log.error("未知异常: ", e);
        return Result.error(BaseCode.SYSTEM_ERROR.getCode(), "系统错误，请稍后重试!");
    }

    /**
     * 处理参数校验异常(BindException)
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e) {
        log.error("参数校验异常: ", e);
        return Result.error(BaseCode.ARGUMENT_ERROR);
    }

}