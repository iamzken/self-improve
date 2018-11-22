package com.gupao;

import com.gupao.api.IGpService;
import com.gupao.proxy.RpcClientProxy;
import com.gupao.registry.IServiceDiscovery;
import com.gupao.registry.ServiceDiscoveryImpl;

/**
 * Created by Jack
 * Create in 21:34 2018/9/1
 * Description:
 */
public class ClientTest {
    public static void main(String[] args) {
        IServiceDiscovery serviceDiscovery=new ServiceDiscoveryImpl();
        RpcClientProxy rpcClientProxy=new RpcClientProxy(serviceDiscovery);
        //无感知的调用
        IGpService iGpService= rpcClientProxy.create(IGpService.class);
        //System.out.println(iGpService);
        System.out.println(iGpService.hello("Jack！！！"));
    }
}
