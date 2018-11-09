package com.hua.miaosha.common;

public enum CodeEnums {

    SUCCESS(0, "success"),
    Error(-1,"error"),

    //登入异常 5002XX
    LOGINOUTTIME(500201,"登入超时"),
    MOBILE_EMPTY(500202,"手机不能为空"),
    PASSWORD_EMPTY(500203,"密码不能为空"),
    MOBILE_ERROR(500204,"手机号码格式错误"),
    USER_EMPTY(500205,"该用户不存在"),
    PASSWORD_ERROR(500206,"密码错误"),


    //商品异常  5003XX

    //订单异常   5004XX

    //秒杀异常   5004XX
    MIAOSHA_OVER(500401,"秒杀结束"),
    REPEATE_MIAOSHA(500402,"已参加过秒杀");



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
            case 500202:
                return MOBILE_EMPTY;
            case 500203:
                return PASSWORD_EMPTY;
            case 500204:
                return MOBILE_ERROR;
            case 500205:
                return USER_EMPTY;
            case 500206:
                return PASSWORD_ERROR;
            case 500401:
                return MIAOSHA_OVER;
            case 500402:
                return REPEATE_MIAOSHA;
            default:
                return null;
        }
    }


}
