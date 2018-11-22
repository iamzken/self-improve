package com.gupaoedu.dubbo.consumer.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.gupaoedu.dubbo.core.msg.InvokerMsg;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class RpcProxy {  
	
	@SuppressWarnings("unchecked")
	public static <T> T create(Class<?> clazz){        
        MethodProxy methodProxy = new MethodProxy(clazz);        
		T result = (T)Proxy.newProxyInstance(clazz.getClassLoader(),new Class[] { clazz },methodProxy); 
        return result;
    }
}


class MethodProxy implements InvocationHandler {
	private Class<?> clazz;
	public MethodProxy(Class<?> clazz){
		this.clazz = clazz;
	}
	
	@Override
    public Object invoke(Object proxy, Method method, Object[] args)  throws Throwable {        
        //如果传进来是一个已实现的具体类（本次演示略过此逻辑)
        if (Object.class.equals(method.getDeclaringClass())) {  
            try {  
                return method.invoke(this, args);  
            } catch (Throwable t) {  
                t.printStackTrace();  
            }  
        //如果传进来的是一个接口（核心)
        } else {  
            return rpcInvoke(proxy,method, args);  
        }  
        return null;
    }
	
	
    /**
     * 实现接口的核心方法 
     * @param method
     * @param args
     * @return
     */
    public Object rpcInvoke(Object proxy,Method method,Object[] args){
    	  InvokerMsg msg = new InvokerMsg();
	      msg.setClassName(this.clazz.getName());
	      msg.setMethodName(method.getName());
	      msg.setValues(args);
	      msg.setParames(method.getParameterTypes());
	  	
	      final RpcProxyHandler consumerHandler = new RpcProxyHandler();
	      EventLoopGroup group = new NioEventLoopGroup();
	      try {
	          Bootstrap b = new Bootstrap();
	          b.group(group)
	           .channel(NioSocketChannel.class)
	           .option(ChannelOption.TCP_NODELAY, true)
	           .handler(new ChannelInitializer<SocketChannel>() {
	               @Override
	               public void initChannel(SocketChannel ch) throws Exception {
	                   ChannelPipeline pipeline = ch.pipeline();
	                   pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
	                   pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
	                   pipeline.addLast("encoder", new ObjectEncoder());
	                   pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
	                   pipeline.addLast("handler",consumerHandler);
	               }
	           });
	  
	          
	          ChannelFuture future = b.connect("localhost", 8080).sync();
	          future.channel().writeAndFlush(msg).sync();
	          future.channel().closeFuture().sync();
	      } catch(Exception e){
	      	e.printStackTrace();
	      }finally {    
	          group.shutdownGracefully();
	      }  
	      return consumerHandler.getResponse();
    } 

}
