package com.gupao.proxy;

import com.gupao.bean.RpcRequest;
import com.gupao.registry.IServiceDiscovery;
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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Jack
 * Create in 22:10 2018/9/1
 * Description:
 */
public class RpcClientProxy {
    private IServiceDiscovery serviceDiscovery;

    public RpcClientProxy(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    //一定要是通用
    public <T> T create(final Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass}, new InvocationHandler() {
                    //这里实际上是封装RpcRequest请求对象，然后通过Netty发给服务端
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //封装RpcRequest对象
                        RpcRequest request = new RpcRequest();
                        request.setClassName(method.getDeclaringClass().getName());
                        request.setMethodName(method.getName());
                        request.setTypes(method.getParameterTypes());
                        request.setParams(args);

                        //服务发现，因为接下来需要进行通信了，IGpService
                        String serviceName = interfaceClass.getName();
                        //url 地址
                        String serviceAddress = serviceDiscovery.discover(serviceName);

                        //解析host和ip
                        String[] arrs = serviceAddress.split(":");
                        String host = arrs[0];
                        int port = Integer.parseInt(arrs[1]);
                        //Socket   Netty连接

                        final RpcProxyHandler rpcProxyHandler = new RpcProxyHandler();
                        //通过netty的方式进行连接和发送
                        EventLoopGroup group = new NioEventLoopGroup();
                        try {
                            Bootstrap b = new Bootstrap();
                            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                                    .handler(new ChannelInitializer<SocketChannel>() {
                                        @Override
                                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                                            ChannelPipeline pipeline = socketChannel.pipeline();
                                            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                                            pipeline.addLast("encoder", new ObjectEncoder());
                                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                                            pipeline.addLast("handler", rpcProxyHandler);
                                        }
                                    });
                            //连接服务地址
                            ChannelFuture future = b.connect(host, port).sync();
                            //将封装好的request对象写过去
                            future.channel().writeAndFlush(request);
                            future.channel().closeFuture().sync();
                        } catch (Exception e) {

                        } finally {
                            group.shutdownGracefully();
                        }
                        return rpcProxyHandler.getResponse();
                    }
                });
    }
}





