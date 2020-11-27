package com.wll.common.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class RetData<T> {

    private static final long serialVersionUID = 1577677467648L;

    /** 状态码 */
    private int ret;
    /** 状态信息 */
    private String msg;

    private T data;

    public static RetData<Void> success(){
        RetData<Void> retData = new RetData<Void>();
        retData.setRet(0);
        retData.setMsg("操作成功");
        return retData;
    }

    public static <T> RetData<T> result(Ret ret){
        RetData<T> retData = new RetData<T>();
        retData.setRet(ret.getCode());
        retData.setMsg(ret.getMsg());
        return retData;
    }

    public static <T> RetData<T> result(int code,String msg){
        RetData<T> retData = new RetData<T>();
        retData.setRet(code);
        retData.setMsg(msg);
        return retData;
    }

    public static <T> RetData<T> result(T t){
        RetData<T> retData = new RetData<T>();
        retData.setRet(0);
        retData.setMsg("操作成功");
        retData.setData(t);
        return retData;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
