package com.gupaoedu.mybatis.gp.executor;

import com.gupaoedu.mybatis.gp.config.MapperRegistory;

/**
 * Created by James on 2017-07-01.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public interface Executor {

    <T> T query(MapperRegistory.MapperData mapperData, Object parameter) throws Exception;
}
