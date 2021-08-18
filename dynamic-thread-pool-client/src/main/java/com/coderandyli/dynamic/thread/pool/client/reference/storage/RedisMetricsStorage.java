package com.coderandyli.dynamic.thread.pool.client.reference.storage;

import com.coderandyli.dynamic.thread.pool.client.reference.MetricsStorage;
import com.coderandyli.dynamic.thread.pool.client.reference.RequestInfo;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
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
    private static final List<RequestInfo> requestInfos = new ArrayList<>();

    @Override
    public void saveRequestInfo(RequestInfo requestInfo) {
        requestInfos.add(requestInfo);
    }

    @Override
    public List<RequestInfo> getRequestInfosByDuration(String apiName, long startTime, long endTime) {
        return null;
    }

    @Override
    public Map<String, List<RequestInfo>> getAllRequestInfosByDuration(long startTime, long endTime) {
        if (CollectionUtils.isEmpty(requestInfos)) return null;

        Map<String, List<RequestInfo>> resultMap = new HashMap<>();
        for (RequestInfo requestInfo : requestInfos) {
            long timestamp = requestInfo.getTimestamp();
            if (startTime < timestamp && timestamp < endTime){
                String apiName = requestInfo.getApiName();
                List<RequestInfo> requestInfoSubList = resultMap.get(apiName);
                if (requestInfoSubList == null){
                    requestInfoSubList = new ArrayList<>();
                }
                requestInfoSubList.add(requestInfo);
                resultMap.put(apiName, requestInfoSubList);
            }
        }

        return resultMap;
    }
}
