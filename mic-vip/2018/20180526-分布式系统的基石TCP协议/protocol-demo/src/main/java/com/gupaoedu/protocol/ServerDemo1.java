package com.gupaoedu.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ServerDemo1 {

    public static void main(String[] args) {
        ServerSocket server=null;
        try{
            server=new ServerSocket(8080);
            Socket socket=server.accept(); //阻塞过程

            BufferedReader is=new BufferedReader
                    (new InputStreamReader(socket.getInputStream()));

            PrintWriter os=new PrintWriter(socket.getOutputStream());

            BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Client:"+is.readLine()); //拿到客户端的信息

            String line=sin.readLine();
            while(!line.equals("bye")){
                os.println(line);//输出数据
                os.flush();
                System.out.println("Server:"+line);
                System.out.println("Client:"+is.readLine());
                line=sin.readLine();
            }
            os.close();
            is.close();
            socket.close();



        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }
}
