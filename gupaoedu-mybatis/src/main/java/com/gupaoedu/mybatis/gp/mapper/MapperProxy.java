package com.gupaoedu.mybatis.gp.mapper;

import com.gupaoedu.mybatis.gp.config.MapperRegistory;
import com.gupaoedu.mybatis.gp.session.GpSqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by James on 2017-07-01.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class MapperProxy<T> implements InvocationHandler {
    private final GpSqlSession sqlSession;
    private final Class<T> mappperInterface;

    public MapperProxy(GpSqlSession gpSqlSession, Class<T> clazz) {
        this.sqlSession = gpSqlSession;
        this.mappperInterface = clazz;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperRegistory.MapperData mapperData =
                sqlSession.getConfiguration()
                        .getMapperRegistory()
                        .get(method.getDeclaringClass().getName() + "." + method.getName());
        if (null != mapperData) {
            System.out.println(String.format("SQL [ %s ], parameter [%s] ", mapperData.getSql(), args[0]));
            return sqlSession.selectOne(mapperData, String.valueOf(args[0]));
        }
        return method.invoke(proxy, args);
    }
}
