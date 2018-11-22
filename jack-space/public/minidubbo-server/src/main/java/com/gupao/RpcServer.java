package com.gupao;

import com.gupao.registry.IServiceRegistryCenter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack
 * Create in 21:41 2018/9/1
 * Description:
 */
public class RpcServer {
    //1.服务注册，一定要获得要注册的服务名称，服务地址   缺少服务名称
    //2.监听   根据serviceAddress进行监听
    private IServiceRegistryCenter serviceRegistryCenter;
    private String serviceAddress;

    //key:服务名称   value:服务对象
    Map<String,Object> handlerMap=new HashMap<String,Object>();

    public RpcServer(IServiceRegistryCenter serviceRegistryCenter, String serviceAddress) {
        this.serviceRegistryCenter = serviceRegistryCenter;
        this.serviceAddress = serviceAddress;
    }

    //使用一个方法bind：服务名称---服务对象   IGpService-----实现类  GpServiceImpl   不想等你Client   IGpService--->不知道
    public void bind(Object... services){
        //遍历出传入了多少个服务对象    注解    @Service  @Autowired
        //@RpcService----->为了在实现类上标注我当前的类所属的接口是是
        for(Object service:services){
            //service.getClass---->com.gupao.impl.GpServiceImpl
            RpcService annotation = service.getClass().getAnnotation(RpcService.class);
            String serviceName=annotation.value().getName();
            //key(serviceName):com.gupao.api.IGpService
            //value(service):com.gupao.impl.GpServiceImpl
            handlerMap.put(serviceName,service);
        }
    }

    public void registerAndListen(){
        //1 注册服务地址，先遍历一下handlerMap中有多少服务名称
        for (String serviceName : handlerMap.keySet()) {
            //注册服务名称和服务地址
            serviceRegistryCenter.register(serviceName, serviceAddress);
        }



        //2 要监听端口，并且要进行与客户端通信   Socket
        //Netty   NIO
        try {
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            //启动netty的服务
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            //springmvc   web.xml    DispatherServlet    Handler
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    //业务代码在这边写就ok了     SpringMVC   Handler   Action
                    ChannelPipeline pipeline = channel.pipeline();
                    //如果你用netty进行编程的话，只需要关注Handler即可  NIO   BIO
                    //pipeline.addLast(Handler())
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                    pipeline.addLast(new LengthFieldPrepender(4));
                    pipeline.addLast("encoder", new ObjectEncoder());
                    pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                    //假如自己要写一个handler   一定是和客户端进行打交道的    Socket.getI
                    //业务请求 ------>Netty   IO请求    ------>交互   Client传来的数据是什么   Server要写过去的数据
                    //Netty
                    pipeline.addLast(new RpcServerHandler(handlerMap));
                }
            }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
            //通过netty进行监听    8080
            String[] addrs = serviceAddress.split(":");
            String ip = addrs[0];
            int port = Integer.parseInt(addrs[1]);
            ChannelFuture future = bootstrap.bind(ip, port).sync();
            System.out.println("netty服务端启动成功，等待客户端的连接:");
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}





