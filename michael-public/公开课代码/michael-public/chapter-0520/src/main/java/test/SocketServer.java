package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SocketServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server=null;
        try {
            server = new ServerSocket(8080);
            Socket socket = server.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(in.readLine());
            in.close();
        }finally {
            if(server!=null) {
                server.close();
            }
        }
    }
}
