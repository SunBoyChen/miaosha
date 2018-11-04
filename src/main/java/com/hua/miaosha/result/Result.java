package com.hua.miaosha.result;


import com.hua.miaosha.common.CodeEnums;

public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    public Result(CodeEnums enums, T data) {
        this.code = enums.getCode();
        this.msg = enums.getDesc();
        this.data = data;
    }

    public static Result<Boolean>  successResult(CodeEnums enums){
        return new Result<>(enums,true);
    }


    public static Result<Boolean>  errorResult(CodeEnums enums){
        return new Result<>(enums,false);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
