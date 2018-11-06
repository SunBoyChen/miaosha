package com.hua.miaosha.exception;

public class GlobalException extends RuntimeException {


    private Integer code;

    public GlobalException(Integer code,String msg) {
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
