package com.gupaoedu.mybatis.gp.session;

import com.gupaoedu.mybatis.gp.config.GpConfiguration;
import com.gupaoedu.mybatis.gp.config.MapperRegistory;
import com.gupaoedu.mybatis.gp.executor.Executor;
import com.gupaoedu.mybatis.gp.mapper.MapperProxy;

import java.lang.reflect.Proxy;

/**
 * Created by James on 2017-07-01.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class GpSqlSession {
    private GpConfiguration configuration;
    private Executor executor;

    public GpConfiguration getConfiguration() {
        return configuration;
    }

    //关联起来
    public GpSqlSession(GpConfiguration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},new MapperProxy(this,clazz));
    }

    public <T> T selectOne(MapperRegistory.MapperData mapperData, Object parameter) throws Exception {
        return executor.query(mapperData, parameter);
    }
}
