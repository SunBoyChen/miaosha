package com.hua.miaosha.common;

public enum CodeEnums {

    SUCCESS(0, "success"),
    Error(-1,"error"),

    //登入异常 5002XX
    LOGINOUTTIME(500201,"登入超时");

    //商品异常  5003XX

    //订单异常   5004XX

    //秒杀异常   5004XX


    private int code;

    private String desc;


    CodeEnums(int code,String desc){
        this.code = code;
        this.desc = desc;
    };

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public  static  CodeEnums valueOf(int value){

        switch (value) {
            case 0:
                return SUCCESS;
            case -1:
                return Error;
            case 500201:
                return LOGINOUTTIME;
            default:
                return null;
        }
    }


}
