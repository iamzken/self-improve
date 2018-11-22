package com.gupaoedu.mybatis.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * Created by James on 2017-10-28.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public class AspectsMain {

    public static void main(String[] args) {
        TargetObject targetObject = new TargetObject();
        // create a factory that can generate a proxy for the given target object
        AspectJProxyFactory factory = new AspectJProxyFactory(targetObject);

// add an aspect, the class must be an @AspectJ aspect
// you can call this as many times as you need with different aspects
        factory.addAspect(MyAdvice.class);

// you can also add existing aspect instances, the type of the object supplied must be an @AspectJ aspect
//        factory.addAspect(usageTracker);

// now get the proxy object...
        TargetObject proxy = factory.getProxy();
        proxy.foo();
    }

}
