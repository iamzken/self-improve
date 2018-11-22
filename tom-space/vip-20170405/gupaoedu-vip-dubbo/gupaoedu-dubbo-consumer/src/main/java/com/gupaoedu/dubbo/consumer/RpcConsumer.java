package com.gupaoedu.dubbo.consumer;

import com.gupaoedu.dubbo.api.IRpcCalc;
import com.gupaoedu.dubbo.api.IRpcHello;
import com.gupaoedu.dubbo.consumer.proxy.RpcProxy;

public class RpcConsumer {
	
    public static void main(String [] args){
        IRpcHello rpcHello = RpcProxy.create(IRpcHello.class);
        
        System.out.println(rpcHello.hello("Tom老师"));
        
        
        IRpcCalc calc = RpcProxy.create(IRpcCalc.class);
        
        //从RMI到RPC，看透Dubbo实现原理
        
        int a = 10,b = 2;
        System.out.println(a + " + " + b + " = " + calc.add(a, b));
        System.out.println(a + " - " + b + " = " + calc.sub(a, b));
        System.out.println(a + " * " + b + " = " + calc.mult(a, b));
        System.out.println(a + " / " + b + " = " + calc.div(a, b));
    }
    
}
