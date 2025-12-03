package com.xxxyjade.hiphopghetto.enums;

/**
 * 错误码枚举类
 */
public enum BaseCode {
    // 通用错误码 0-99
    SUCCESS(200, "响应成功"),
    SYSTEM_ERROR(1, "系统错误"),
    ARGUMENT_ERROR(2, "参数校验错误"),
    
    // 认证授权错误码 100-199
    VERIFY_ERROR(100, "密码校验错误"),
    USER_NOT_FOUND(101, "用户不存在"),
    USER_EXIST(102, "用户已存在"),
    PASSWORDS_DIFFERENT(103, "两次密码不一致"),
    UNAUTHORIZED(104, "未授权访问"),
    ACCESS_DENIED(105, "访问被拒绝"),
    
    // 业务错误码 200-299
    RESOURCE_NOT_FOUND(200, "资源不存在"),
    RESOURCE_ALREADY_EXIST(201, "资源已存在"),
    OPERATION_NOT_ALLOWED(202, "操作不被允许"),
    
    // 数据库相关错误码 300-399
    DATABASE_EXCEPTION(300, "数据库异常"),
    DATABASE_CONNECTION_ERROR(301, "数据库连接异常");

    private final Integer code;

    private String msg = "";

    BaseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg == null ? "" : this.msg;
    }

    public static String getMsg(Integer code) {
        for (BaseCode re : BaseCode.values()) {
            if (re.code.intValue() == code.intValue()) {
                return re.msg;
            }
        }
        return "";
    }

    public static BaseCode getCode(Integer code) {
        for (BaseCode re : BaseCode.values()) {
            if (re.code.intValue() == code.intValue()) {
                return re;
            }
        }
        return null;
    }
}