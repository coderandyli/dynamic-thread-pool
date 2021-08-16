package com.coderandyli.dynamic.thread.pool.client.monitor.viewer;


import com.coderandyli.dynamic.thread.pool.client.monitor.RequestStat;
import com.coderandyli.dynamic.thread.pool.client.monitor.StatViewer;
import com.google.gson.Gson;

import java.util.Map;

public class ConsoleViewer implements StatViewer {
    @Override
    public void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMills) {
        System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMills + "]");
        Gson gson = new Gson();
        System.out.println(gson.toJson(requestStats));
    }
}
