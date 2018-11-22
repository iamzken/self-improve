package com.gupaoedu.mybatis.gp.executor;

import com.gupaoedu.mybatis.gp.config.GpConfiguration;
import com.gupaoedu.mybatis.gp.config.MapperRegistory;
import com.gupaoedu.mybatis.gp.statement.StatementHandler;

public class SimpleExecutor implements Executor {
    private GpConfiguration configuration;

    public SimpleExecutor(GpConfiguration configuration) {
        this.configuration = configuration;
    }

    public GpConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(GpConfiguration configuration) {
        this.configuration = configuration;
    }

    public <E> E query(MapperRegistory.MapperData mapperData, Object parameter)
            throws Exception {
        //初始化StatementHandler --> ParameterHandler --> ResultSetHandler
        StatementHandler handler = new StatementHandler(configuration);
        return (E) handler.query(mapperData, parameter);
    }
}