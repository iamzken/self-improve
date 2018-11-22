package com.gupaoedu.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.List;

/**
 * @Author: qingshan
 * @Date: 2018/11/13 14:39
 * @Description: 咕泡学院，只为更好的你
 */
public class MyDataflowJob implements DataflowJob {
    @Override
    public List fetchData(ShardingContext shardingContext) {
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List list) {

    }
}
