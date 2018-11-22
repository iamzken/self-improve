package com.gupao;

import com.gupao.api.IGpService;
import com.gupao.impl.GpServiceImpl;
import com.gupao.registry.IServiceRegistryCenter;
import com.gupao.registry.ZkServiceRegistryCenterImpl;

import java.io.IOException;

/**
 * Created by Jack
 * Create in 21:16 2018/9/1
 * Description:
 */
public class ServerTest {
    public static void main(String[] args) throws IOException {
        IGpService iGpService=new GpServiceImpl();
        IServiceRegistryCenter serviceRegistryCenter=new ZkServiceRegistryCenterImpl();
        String serviceAddress="127.0.0.1:8080";
        //RpcServer类去处理            注册，监听
        RpcServer rpcServer=new RpcServer(serviceRegistryCenter,serviceAddress);
        rpcServer.bind(iGpService);
        rpcServer.registerAndListen();
    }

}

//希望把这个服务注册到zk上
        /*IServiceRegistryCenter serviceRegistryCenter=new ZkServiceRegistryCenterImpl();
        serviceRegistryCenter.register("com.gupao.api.IGpService","127.0.0.1");*/
//java是一门面向对象开发语言   RpcServer:专门用来进行服务注册
//RpcServer rpcServer=new RpcServer(serviceRegistryCenter,serviceAddress);
//com.gupao.impl.GpServiceImpl@327471b5
//注解  写过注解
