//package com.gupao.dal.plugins;
//
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//
//import java.util.Properties;
//
///**
// * Created by James on 2017-06-25.
// * From 咕泡学院出品
// * 老师咨询 QQ 2904270631
// */
//@Intercepts({@Signature(type = Executor.class,
//        method = "query",
//        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
//public class GPPlugin  implements Interceptor {
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        return null;
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return null;
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//}
