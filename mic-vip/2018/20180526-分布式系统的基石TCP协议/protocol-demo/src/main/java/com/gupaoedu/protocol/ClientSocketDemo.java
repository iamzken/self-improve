package com.gupaoedu.protocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ClientSocketDemo {

    public static void main(String[] args) throws IOException {
        Socket socket=null;
        try{
            socket=new Socket("127.0.0.1",8080);
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
            out.println("Hello");
        }catch (Exception e){

        }finally {
            if(socket!=null) {
                socket.close();
            }
        }
    }
}
