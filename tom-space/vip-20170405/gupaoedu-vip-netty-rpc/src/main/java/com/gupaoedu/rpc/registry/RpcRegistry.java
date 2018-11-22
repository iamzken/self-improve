package com.gupaoedu.rpc.registry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class RpcRegistry {  
    private int port;  
    public RpcRegistry(int port){  
        this.port = port;  
    }  
    public void start(){  
        EventLoopGroup bossGroup = new NioEventLoopGroup();  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
          
        try {  
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
            		.channel(NioServerSocketChannel.class)  
                    .childHandler(new ChannelInitializer<SocketChannel>() {
  
                        @Override  
                        protected void initChannel(SocketChannel ch) throws Exception {  
                            ChannelPipeline pipeline = ch.pipeline();    
                             pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));    
                                pipeline.addLast(new LengthFieldPrepender(4));    
                                pipeline.addLast("encoder", new ObjectEncoder());      
                                pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));    
                                pipeline.addLast(new RegistryHandler());   
                        }  
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)       
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  
            ChannelFuture future = b.bind(port).sync();      
            System.out.println("RPC Registry start listen at " + port );    
            future.channel().closeFuture().sync();    
        } catch (Exception e) {  
             bossGroup.shutdownGracefully();    
             workerGroup.shutdownGracefully();  
        }  
    }
    
    
    public static void main(String[] args) throws Exception {    
        new RpcRegistry(8080).start();    
    }    
}  
