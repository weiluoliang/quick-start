package com.wll.common.uitl;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RequestUtils {

    public static String getRealIp(HttpServletRequest request){
        String ip = request.getHeader("X-Real-IP");
        if(StringUtils.isBlank(ip)){
            ip = request.getRemoteHost();
        }
        return ip;
    }

    public static String getReqData(HttpServletRequest request) {
        if(request.getMethod().equalsIgnoreCase("GET")){
            return getParamData(request);
        }
        String contentType = request.getContentType();
        if(contentType == null){
            return getParamData(request);
        }
        if(contentType.startsWith("multipart/form-data")){
            return getParamData(request);
        }
        if(contentType.startsWith("application/x-www-form-urlencoded")){
            return getParamData(request);
        }
        int contentLength = request.getContentLength();
        if(contentLength <= 0 ){
            return "";
        }
        try {
            ServletInputStream inputStream = request.getInputStream();
            byte[] bytes = new byte[contentLength];
            inputStream.read(bytes);
            return new String(bytes,request.getCharacterEncoding());
        } catch (IOException e) {
            log.error("err ", e);
            return null;
        }
    }

    private static String getParamData(HttpServletRequest request) {
        Map<String, String[]> data = request.getParameterMap();
        JSONObject json = new JSONObject();
        data.forEach((key,val)-> json.put(key,StringUtils.join(val)));
        return json.toJSONString();
    }

    /**
     * 将异步通知的参数转化为Map
     * @param request {HttpServletRequest}
     * @return 转化后的Map
     */
    public static Map<String, String> toMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        requestParams.forEach((name,values)->{
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        });
        return params;
    }

}
