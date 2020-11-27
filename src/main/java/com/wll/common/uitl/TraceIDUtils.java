package com.wll.common.uitl;

import org.slf4j.MDC;

import java.util.UUID;

public class TraceIDUtils {

    private static final String TraceId = "traceId";
    private static final String UserId = "userId";
    private static final String IP = "ip";

    public static void addTraceId(){
        String traceId = UUID.randomUUID().toString().replaceAll("-", "");
        MDC.put(TraceId, traceId);
    }

    public static void addTraceId(String traceId){
        if(traceId == null){
            traceId = UUID.randomUUID().toString().replaceAll("-", "");
        }
        MDC.put(TraceId, traceId);
    }

    public static void addUserId(Long userId){
        if(userId==null) {
            return;
        }
        MDC.put(UserId,String.valueOf(userId));
    }

    public static String getTraceId(){
        return MDC.get(TraceId);
    }

    public static void addIp(String ip){
        MDC.put(IP,ip);
    }

    public static String getIp(){
        return  MDC.get(IP);
    }

    public static void remove(){
        MDC.remove(TraceId);
        MDC.remove(UserId);
        MDC.remove(IP);
    }

}
