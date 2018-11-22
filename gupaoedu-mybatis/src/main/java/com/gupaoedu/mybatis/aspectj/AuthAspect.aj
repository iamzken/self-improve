package com.gupaoedu.mybatis.aspectj;

/**
 * Created by James on 2017-10-28.
 * From 咕泡学院出品
 * 老师咨询 QQ 2904270631
 */
public aspect AuthAspect {
// Advice
// execution(* com.mybry.aop.service.*.*(..)执行 任意返回值 改包下的任意类的任意方法形参不限
    before():execution(* *.*(..)){
// 对原来方法进行修改、增强。
        System.out.println("----------模拟执行权限检查----------");
    }
}
