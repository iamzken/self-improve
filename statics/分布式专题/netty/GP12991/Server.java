package com.bio.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int SERVER_PORT=7777;

 //   private static Map<String,Socket> threadMap=new ConcurrentHashMap<String, Socket>();
    
    public  static List<Socket> list=Collections.synchronizedList(new ArrayList<>());

    private static ExecutorService expool=Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
    	 ServerSocket serverSocket = null;
         try {
             serverSocket = new ServerSocket(SERVER_PORT);
              while (true){
                 Socket socket=serverSocket.accept();
                 String  threadId=socket.getInetAddress().getHostAddress()+":"+socket.getPort();
                 System.out.println(threadId+"接入服务");
                 ServerHandler handler=new ServerHandler(socket);
                 list.add(socket);
                  expool.submit(handler);
             }
         } catch (IOException e) {
             e.printStackTrace();
         }

    }
}
