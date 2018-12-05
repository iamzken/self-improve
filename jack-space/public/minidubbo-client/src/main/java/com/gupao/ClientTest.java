package com.gupao;

import com.gupao.api.IGpService;
import com.gupao.bean.RpcRequest;
import com.gupao.proxy.RpcClientProxy;
import com.gupao.proxy.RpcProxyHandler;
import com.gupao.registry.IServiceDiscovery;
import com.gupao.registry.ServiceDiscoveryImpl;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

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
        System.out.println(iGpService.hello("conan！！！"));

    }

}
