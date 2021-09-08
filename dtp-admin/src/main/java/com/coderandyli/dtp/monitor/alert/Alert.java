package com.coderandyli.dtp.monitor.alert;

import com.coderandyli.dtp.monitor.alert.handler.AlertHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 警告类
 *
 * @Date 2021/8/26 1:44 下午
 * @Created by lizhenzhen
 */
@Component
public class Alert {
    /**
     * 警告处理器集合
     */
    private final List<AlertHandler> alertHandlers = new ArrayList<>();

    public void addAlertHandler(AlertHandler handler) {
        alertHandlers.add(handler);
    }

    public void check(StatInfo statInfo) {
        for (AlertHandler handler : alertHandlers) {
            handler.check(statInfo);
        }
    }
}
