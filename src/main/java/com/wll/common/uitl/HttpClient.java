package com.wll.common.uitl;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpClient {

    private static RestTemplate restTemplate = new RestTemplate();

    public static String get(String url){
        return restTemplate.getForObject(url, String.class);
    }

    public static <T> T get(String url,Class<T> clazz){
        return restTemplate.getForObject(url,clazz);
    }

    public static String postForm(String url, MultiValueMap<String, String> params){
        return restTemplate.postForObject(url,params,String.class);
    }

    public static String get(String url, Map<String,Object> params, Map<String,String> headers){
        HttpHeaders httpHeaders = new HttpHeaders();
        if(headers != null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.set(entry.getKey(),entry.getValue());
            }
        }
        HttpEntity<String> entity = new HttpEntity<String>(null,httpHeaders);
        ResponseEntity<byte[]> exchange = restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class, params);
        return exchange.getBody()==null?null:new String(exchange.getBody());
    }


    public static ResponseEntity<byte[]> doRequest(String url, String body, Map<String,String> headers){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if(headers != null){
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.set(entry.getKey(),entry.getValue());
            }
        }
        HttpEntity<String> entity = new HttpEntity<String>(body,httpHeaders);
        return restTemplate.exchange(url, HttpMethod.POST, entity, byte[].class);
    }

}
