package com.coderandyli.dynamic.thread.pool.client.monitor.view;

import com.coderandyli.dynamic.thread.pool.client.monitor.TaskStat;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
