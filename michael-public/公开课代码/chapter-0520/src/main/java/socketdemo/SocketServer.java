package socketdemo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class SocketServer {

    private static int DEFAULT_PORT=8080;

    private static ServerSocket server;

    public static void start() throws IOException {
        start(DEFAULT_PORT);
    }

    public synchronized static void start(int port) throws IOException {
        if(server!=null) return;
        try {
            server = new ServerSocket(port);
            System.out.println("服务器启动成功:端口号:" + port);
            Socket socket;
            while (true) {
                socket = server.accept();
                new Thread(new ServerHandler(socket)).start();
            }
        }finally {
            if(server!=null){
                server.close();
                System.out.println("服务器关闭");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        start();
    }
}
