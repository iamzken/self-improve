package socketdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SocketClient {

    public static void send(String request){
        send(8080,request);
    }
    public static void send(int port,String request){
        Socket socket=null;
        BufferedReader in=null;
        PrintWriter out=null;
        try{
            socket=new Socket("127.0.0.1",port);
            out=new PrintWriter(socket.getOutputStream(),true);
            out.print(request);
            /*in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("服务端返回结果为："+in.readLine());*/
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                out.close();
            }
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
