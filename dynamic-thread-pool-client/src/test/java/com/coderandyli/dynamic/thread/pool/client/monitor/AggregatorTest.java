package com.coderandyli.dynamic.thread.pool.client.monitor;

import com.coderandyli.dynamic.thread.pool.client.reference.RequestStat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Date 2021/8/19 5:55 下午
 * @Created by lizhenzhen
 */
public class AggregatorTest {

    private Aggregator aggregator;

    @Before
    public void before() {
        aggregator = new Aggregator();
    }


    @Test
    public void doAggregate() {
        List<ThreadTaskInfo> taskInfos = new ArrayList<>();

        for (int i = 500; i > 0 ; i--) {
            ThreadTaskInfo taskInfo1 = new ThreadTaskInfo();
            taskInfo1.setTaskName("taskname01");
            taskInfo1.setTimestamp(System.currentTimeMillis() - 3000);
            taskInfo1.setResponseTime(i);
            taskInfos.add(taskInfo1);
        }


        RequestStat requestStat = aggregator.doAggregate(taskInfos, 3 * 1000);
        System.out.println(requestStat.toString());

        Assert.assertEquals(requestStat.getMinResponseTime(), 1.0d, 0.1d);
        Assert.assertEquals(requestStat.getMaxResponseTime(), 500d, 0.1d);
        Assert.assertEquals(requestStat.getP99ResponseTime(), 495.0d,0.1d);
        Assert.assertEquals(requestStat.getCount(), 500);
        Assert.assertEquals(requestStat.getTps(), 166);
    }
}