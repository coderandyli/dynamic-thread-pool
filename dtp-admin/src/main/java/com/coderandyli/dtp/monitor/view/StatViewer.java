package com.coderandyli.dtp.monitor.view;

import com.coderandyli.dtp.monitor.TaskStat;

import java.util.Map;

/**
 *  statistics view
 *
 * @Date 2021/8/20 10:19 上午
 * @Created by lizhenzhen
 */
public interface StatViewer {
    void output(Map<String, TaskStat> requestStats, long startTimeInMillis, long endTimeInMills);
}
