package com.wll.common.bean;

public enum  ResultEnum implements Ret{

    /** */
    SUCCESS(0,"成功"),
    SYS_ERROR(500,"系统错误，请稍后再试。"),
    INCORRECT_PARAMETERS(9000,"请求参数不足或有误，请检查后再试。"),
    TOKEN_EXPIRED(9001,"登录状态已失效，需重新登录。"),
    ;

    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
