package com.wll.common.monitor.bean;

public enum MonitorTypeEnum {
    SYS("sys", "监控报警"),
    BUSSINESS("business", "通知");

    private String code;
    private String msg;

    private MonitorTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
