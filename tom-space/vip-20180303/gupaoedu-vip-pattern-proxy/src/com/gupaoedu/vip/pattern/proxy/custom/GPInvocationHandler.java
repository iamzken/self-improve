package com.gupaoedu.vip.pattern.proxy.custom;

import java.lang.reflect.Method;

/**
 * Created by Tom on 2018/3/10.
 */
public interface GPInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
