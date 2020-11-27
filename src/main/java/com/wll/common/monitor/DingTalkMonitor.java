package com.wll.common.monitor;

import com.alibaba.fastjson.JSON;
import com.wll.common.bean.Env;
import com.wll.common.monitor.bean.DingTalkReq;
import com.wll.common.monitor.bean.DingTalkText;
import com.wll.common.monitor.bean.MonitorData;
import com.wll.common.monitor.bean.MonitorTypeEnum;
import com.wll.common.uitl.HttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;


/**
 * Description: 钉钉推送<br/>
 * date: 2020/4/17 9:59<br/>
 *
 * @author luoliang<br />
 */
@Slf4j
public class DingTalkMonitor implements Monitor {

    private static final String PUSH_BODY = "[%s][%s][%s]%s";
    /** 应用的名称 */
    private String appName;
    /** 环境 */
    private String env;
    private String url;

    public DingTalkMonitor(String appName, String env, String url) {
        this.url = url;
        this.appName = appName;
        this.env = env;
    }

    @Override
    public void push(MonitorData monitorData) {
        push(monitorData, MonitorTypeEnum.SYS);
    }

    @Override
    public void push(MonitorData monitorData, MonitorTypeEnum type) {
        if(Env.DEV.equals(env)){
            log.info("env dev ignore to push monitor .");
            return;
        }
        DingTalkReq req = new DingTalkReq();
        DingTalkText text = new DingTalkText();
        String data = JSON.toJSONString(monitorData);
        String keyword = type.getMsg();
        text.setContent(String.format(PUSH_BODY,appName,keyword, env,data));
        req.setText(text);
        String reqBody = JSON.toJSONString(req);
        ResponseEntity<byte[]> responseEntity = HttpClient.doRequest(url, reqBody, null);
        String resBody = responseEntity.getBody()==null?"":new String(responseEntity.getBody());
        log.info("DingTalkMonitor push url:{}, :{},res:{}",url,reqBody,resBody);
    }

}
