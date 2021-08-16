package com.coderandyli.dynamic.thread.pool.client.monitor.storage;

import com.coderandyli.dynamic.thread.pool.client.monitor.MetricsStorage;
import com.coderandyli.dynamic.thread.pool.client.monitor.RequestInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author lizhen
 * @version 1.0
 * @date 2020/1/3 上午9:51
 *
 * 数据存储到Redis
 */
@Repository
public class RedisMetricsStorage implements MetricsStorage {
    @Override
    public void saveRequestInfo(RequestInfo requestInfo) {

    }

    @Override
    public List<RequestInfo> getRequestInfosByDuration(String apiName, long startTime, long endTime) {
        return null;
    }

    @Override
    public Map<String, List<RequestInfo>> getAllRequestInfosByDuration(long startTime, long endTime) {
        return null;
    }
}
