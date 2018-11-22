package com.gupaoedu.rpc.consumer;

import com.gupaoedu.rpc.api.IRpcCalc;
import com.gupaoedu.rpc.api.IRpcHello;
import com.gupaoedu.rpc.consumer.proxy.RpcProxy;

public class RpcConsumer {
	
    public static void main(String [] args){  
        IRpcHello rpcHello = RpcProxy.create(IRpcHello.class);
        
        System.out.println(rpcHello.hello("Tom老师"));
        
        
        IRpcCalc calc = RpcProxy.create(IRpcCalc.class);
        
        System.out.println("8 + 2 = " + calc.add(8, 2));
        System.out.println("8 - 2 = " + calc.sub(8, 2));
        System.out.println("8 * 2 = " + calc.mult(8, 2));
        System.out.println("8 / 2 = " + calc.div(8, 2));
    }
    
}
