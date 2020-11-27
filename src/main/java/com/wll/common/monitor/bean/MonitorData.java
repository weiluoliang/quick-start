package com.wll.common.monitor.bean;

import lombok.Data;

@Data
public class MonitorData {

    private String path;
    private String errorMsg;
    private String traceId;
    private String data;

    public MonitorData() {

    }

    public static MonitorData build(String path, String errorMsg, String traceId, String data) {
        MonitorData monitorData = new MonitorData();
        monitorData.setPath(path);
        monitorData.setErrorMsg(errorMsg);
        monitorData.setTraceId(traceId);
        monitorData.setData(data);
        return monitorData;
    }
}
