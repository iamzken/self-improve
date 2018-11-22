package com.gupaoedu.vip.spring.formework.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.regex.Pattern;

/**
 * Created by Tom on 2018/5/2.
 */
//默认就用JDK动态代理
public class GPAopProxy implements InvocationHandler{

    private GPAopConfig config;
    private Object target;

    //把原生的对象传进来
    public Object getProxy(Object instance){
        this.target = instance;
        Class<?> clazz = instance.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    public void setConfig(GPAopConfig config){
        this.config = config;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Method m = this.target.getClass().getMethod(method.getName(),method.getParameterTypes());


        //作业：利用AOP思想，自己去实现一个TransactionManager
        //你们需要补充：把Method的异常拿到，把Method的方法拿到
        //把Method的参数拿到


        //args的值就是实参
        //我们今天所讲的所有的内容只是客户端的操作API
        //明天我们讲数据库Server端的实现原理


//        method.getExceptionTypes();


        //根据正则匹配
//        String methodName = method.getName();
//        Pattern p = null;
//        if(p.matcher(methodName)){
//            conn.setReadOnly(true);
//        }


        //在原始方法调用以前要执行增强的代码
        //这里需要通过原生方法去找，通过代理方法去Map中是找不到的
        if(config.contains(m)){
           GPAopConfig.GPAspect aspect = config.get(m);
           aspect.getPoints()[0].invoke(aspect.getAspect());
        }

       // try {
            //反射调用原始的方法
            Object obj = method.invoke(this.target, args);
            System.out.println(args);
        //}catch (Exception e){
            //e.getClass();
//            if(e instanceof  Exception){
//                con.rollback();
//            }
        //}
        //在原始方法调用以后要执行增强的代码
        if(config.contains(m)){
            GPAopConfig.GPAspect aspect = config.get(m);
            aspect.getPoints()[1].invoke(aspect.getAspect());
        }

        //将最原始的返回值返回出去
        return obj;
    }
}
