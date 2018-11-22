package com.gupaoedu.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ServerSocketDemo {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=null;
        BufferedReader bufferedReader=null;
        try{
            serverSocket=new ServerSocket(8080);
            Socket socket=serverSocket.accept(); //等待客户端连接
            //获得输入流
            bufferedReader=new BufferedReader
                    (new InputStreamReader(socket.getInputStream()));
            System.out.println(bufferedReader.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(serverSocket!=null){
                serverSocket.close();
            }
        }
    }
}
