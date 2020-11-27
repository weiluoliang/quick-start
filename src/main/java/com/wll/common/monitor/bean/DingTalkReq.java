package com.wll.common.monitor.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class DingTalkReq {

    /** 消息类型 */
    @JSONField(name = "msgtype")
    private String msgType = "text";

    /** 消息内容 */
    private DingTalkText text;

}
