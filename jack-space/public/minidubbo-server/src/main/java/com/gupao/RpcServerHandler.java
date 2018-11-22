package com.gupao;

import com.gupao.bean.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack
 * Create in 21:59 2018/9/1
 * Description:
 */
public class RpcServerHandler extends ChannelInboundHandlerAdapter{

    private Map<String,Object> handlerMap=new HashMap<String,Object>();

    public RpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    //处理你，得到客户端的请求数据，然后根据这个请求数据进行操作，写数据给客户端


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //msg  ----得到客户端传来的数据
        //ctx   ---向客户端写数据
        RpcRequest request=(RpcRequest)msg;

        Object result=new Object();

        //下面就要根据这个request进行调用server的对应类的方法

        if(handlerMap.containsKey(request.getClassName())){
            //执行服务端对应的对象
            Object clazz=handlerMap.get(request.getClassName());
            Method method=clazz.getClass().getMethod(request.getMethodName(),request.getTypes());
            result=method.invoke(clazz,request.getParams());
        }
        //把运行完成的result写给客户端
        ctx.write(result);
        ctx.flush();
        ctx.close();

    }
}
