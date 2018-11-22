package com.gupaoedu.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qingshan
 * @Date: 2018/11/13 14:01
 * @Description: 咕泡学院，只为更好的你
 */
@Slf4j
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info(String.format("------ 任务执行了，Thread ID: %s, 任务总片数: %s, 当前分片项: %s",
                Thread.currentThread().getId(),shardingContext.getShardingTotalCount(), shardingContext.getShardingItem()));
    }
}