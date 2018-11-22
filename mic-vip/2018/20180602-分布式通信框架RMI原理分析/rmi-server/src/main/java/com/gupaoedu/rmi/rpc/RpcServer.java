package com.gupaoedu.rmi.rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 *
 * 用于发布一个远程服务
 */
public class RpcServer {
    //创建一个线程池
    private static final ExecutorService executorService=Executors.newCachedThreadPool();

    public void publisher(final Object service,int port){
        ServerSocket serverSocket=null;
        try{
            serverSocket=new ServerSocket(port);  //启动一个服务监听

            while(true){ //循环监听
                Socket socket=serverSocket.accept(); //监听服务
                //通过线程池去处理请求
                executorService.execute(new ProcessorHandler(socket,service));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
