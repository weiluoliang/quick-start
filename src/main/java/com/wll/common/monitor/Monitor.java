package com.wll.common.monitor;

import com.wll.common.monitor.bean.MonitorData;
import com.wll.common.monitor.bean.MonitorTypeEnum;
import org.springframework.scheduling.annotation.Async;

public interface Monitor {

    @Async
    void push(MonitorData var1);

    @Async
    void push(MonitorData var1, MonitorTypeEnum var2);

}
