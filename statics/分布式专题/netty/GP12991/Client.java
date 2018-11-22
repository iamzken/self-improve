package com.bio.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client  {

    private static final  String SERVER_ADR="192.168.0.100";
    private static  final  int SERVER_PORT=7777;
    
    public static void main(String[] args) throws IOException {

        Socket socket =new Socket(SERVER_ADR,SERVER_PORT);
       
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        new Thread( new ClientHandler(socket)).start();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
    
        new Thread(new ClientHandler(socket)).start();//处理服务端消息
        //获取输入，发送到服务端
        String clientMsg;
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        while ((clientMsg = reader.readLine())!=null){  //输入-1结束发送
            pw.println(clientMsg);
            pw.flush();
        }
            
    }

}
