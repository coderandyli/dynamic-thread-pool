package com.coderandyli.dynamic.thread.pool.monitor.view;

import com.coderandyli.dynamic.thread.pool.monitor.TaskStat;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 控制台输出
 *
 * @Date 2021/8/20 10:35 上午
 * @Created by lizhenzhen
 */
@Component("consoleStatViewer")
public class ConsoleStatViewer implements StatViewer {
    @Override
    public void output(Map<String, TaskStat> requestStats, long startTimeInMillis, long endTimeInMills) {
        System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMills + "]");
        Gson gson = new Gson();
        System.out.println(gson.toJson(requestStats));
    }
}
