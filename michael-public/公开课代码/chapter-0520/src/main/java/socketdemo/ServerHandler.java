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
public class ServerHandler implements Runnable{

    private Socket socket;

    public ServerHandler(Socket socket){
        this.socket=socket;
    }

    public void run() {
        BufferedReader in=null;
        PrintWriter out=null;
        try{
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(),true);
            String question;
            String answer;
            while(true){
                if((question=in.readLine())==null){
                    out.println("空消息");
                }
                System.out.println("服务端收到消息："+question);
                answer="我是服务端返回消息";
                out.print(answer);
            }
        }catch(Exception e){
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
