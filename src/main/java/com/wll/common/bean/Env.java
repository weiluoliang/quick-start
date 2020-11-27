package com.wll.common.bean;

public enum  Env {

    DEV("dev"),
    TEST("test"),
    PROD("prod");

    private String code;

    Env(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
