package com.gupaoedu.mybatis.gp.executor;

import com.gupaoedu.mybatis.gp.config.GpConfiguration;

/**
 * Created by James on 2017-07-01.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class ExecutorFactory {

    private static final String SIMPLE = "SIMPLE";
    private static final String CACHING = "CACHING";


    public static Executor DEFAULT(GpConfiguration configuration) {
        return get(SIMPLE, configuration);
    }

    public static Executor get(String key, GpConfiguration configuration) {
        if (SIMPLE.equalsIgnoreCase(key)) {
            return new SimpleExecutor(configuration);
        }
        if (CACHING.equalsIgnoreCase(key)) {
            return new CachingExecutor(new SimpleExecutor(configuration));
        }
        throw new RuntimeException("no executor found");
    }

    public enum ExecutorType {
        SIMPLE,CACHING
    }
}
