package com.gupao.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Jack
 * Create in 22:24 2018/9/1
 * Description:
 */
public class RpcProxyHandler extends ChannelInboundHandlerAdapter{

    private Object response;
    public Object getResponse(){
        return response;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //msg:服务端写过来的内容
        response=msg;
    }
}
