package com.coderandyli.dynamic.thread.pool.monitor.alert;

import com.coderandyli.dynamic.thread.pool.monitor.alert.handler.AlertHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 警告类
 *
 * @Date 2021/8/26 1:44 下午
 * @Created by lizhenzhen
 */
public class Alert {
    /**
     * 警告处理器集合
     */
    private List<AlertHandler> alertHandlers = new ArrayList<>();

    public void addAlertHandler(AlertHandler handler) {
        alertHandlers.add(handler);
    }

    public void check(StatInfo statInfo) {
        for (AlertHandler handler : alertHandlers) {
            handler.check(statInfo);
        }
    }


}
