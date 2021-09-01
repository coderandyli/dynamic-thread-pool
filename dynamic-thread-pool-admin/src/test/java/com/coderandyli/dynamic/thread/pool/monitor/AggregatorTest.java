package com.coderandyli.dynamic.thread.pool.monitor;

import com.coderandyli.dynamic.thread.pool.client.ThreadTaskInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Date 2021/9/1 4:20 下午
 * @Created by lizhenzhen
 */
public class AggregatorTest {

    private Aggregator aggregator;

    @Before
    public void before() {
        aggregator = new Aggregator();
    }


    @Test
    public void doAggregateTest() {
        List<ThreadTaskInfo> taskInfos = new ArrayList<>();

        for (int i = 500; i > 0 ; i--) {
            ThreadTaskInfo taskInfo1 = new ThreadTaskInfo();
            taskInfo1.setTaskName("taskname01");
            taskInfo1.setTimestamp(System.currentTimeMillis() - 3000);
            taskInfo1.setResponseTime(i);
            taskInfos.add(taskInfo1);
        }


        TaskStat taskStat = aggregator.doAggregate(taskInfos, 3 * 1000);
        System.out.println(taskStat.toString());

        Assert.assertEquals(taskStat.getMinExecuteTime(), 1.0d, 0.1d);
        Assert.assertEquals(taskStat.getMaxExecuteTime(), 500d, 0.1d);
        Assert.assertEquals(taskStat.getP99ExecuteTime(), 495.0d,0.1d);
        Assert.assertEquals(taskStat.getCount(), 500);
        Assert.assertEquals(taskStat.getTps(), 166);
    }

}