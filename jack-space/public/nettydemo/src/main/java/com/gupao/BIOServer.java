package com.gupao;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) {
        server1();
    }

    public static void server1() {
        ServerSocket server = null;
        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            server = new ServerSocket(8000);
            System.out.println("服务端启动成功，监听端口为8000，等待客户端连接...");
            socket = server.accept();
            in = socket.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                System.out.println(new String(buffer, 0, len));
            }
            out = socket.getOutputStream();
            out.write("hello everybody!".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //发现哥们，上面这段代码，有问题，IO的时候，ClientA没有完成，ClientB很着急，连接不上，IO操作不了
    public void server2() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(8000);
            System.out.println("服务端启动成功，监听端口为8000，等待客户端连接...");
            while (true) {
                Socket socket = server.accept();
                //整出一个线程    专门去处理不同的客户端的连接
                new Thread(new ServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void server3() {
        ServerSocket server = null;
        ExecutorService executorService = Executors.newFixedThreadPool(60);
        try {
            server = new ServerSocket(8000);
            System.out.println("服务端启动成功，监听端口为8000，等待客户端连接...");
            while (true) {
                Socket socket = server.accept();
                executorService.execute(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void server4() {
        ServerSocket server = null;
        //Map<Socket,String> map =;
        ExecutorService executorService = Executors.newFixedThreadPool(60);
        try {
            server = new ServerSocket(8000);
            System.out.println("服务端启动成功，监听端口为8000，等待客户端连接...");
            while (true) {
                Socket socket = server.accept();
                //别创建线程，记录Socket A  B   C来了
                //map.put(SocketA,"Connected");
                //一旦你要进行IO操作了   Read   Write
                executorService.execute(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
