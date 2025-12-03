package com.xxxyjade.hiphopghetto.exception;

import com.xxxyjade.hiphopghetto.enums.BaseCode;
import lombok.Data;

@Data
public class HipHopGhettoFrameworkException extends RuntimeException {

    private Integer code;
    private String message;

    public HipHopGhettoFrameworkException(String message) {
        super(message);
        this.message = message;
    }

    public HipHopGhettoFrameworkException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public HipHopGhettoFrameworkException(BaseCode baseCode) {
        super(baseCode.getMsg());
        this.code = baseCode.getCode();
        this.message = baseCode.getMsg();
    }

}
