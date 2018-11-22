package com.gupao;

import com.gupao.api.IGpService;
import com.gupao.registry.IServiceDiscovery;
import com.gupao.registry.ServiceDiscoveryImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
      /* IGpService iGpService=  Netty  RpcRequest  ....交互方式
      对于客户端来讲，调用远程的方法就像调用本地的方法一样，对于用户是无感知的
      动态代理   ------>

      ;//具体的实现类要通过RPC调用的方式获得;
       iGpService.hello("Jack");*/
      //服务发现
        IServiceDiscovery iServiceDiscovery=new ServiceDiscoveryImpl();
        System.out.println(iServiceDiscovery.discover("com.gupao.test"));
    }
}
