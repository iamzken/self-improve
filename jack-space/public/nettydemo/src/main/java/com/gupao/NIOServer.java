package com.gupao;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    private int port = 8000;
    private InetSocketAddress address = null;
    private Selector selector;
    public NIOServer(int port) {
        try {
            this.port = port;
            address = new InetSocketAddress(this.port);
            ServerSocketChannel server = ServerSocketChannel.open();   //ServerSocket  代表服务端的Channel
            server.socket().bind(address);
            //你觉得为什么默认情况下是阻塞的？true
            server.configureBlocking(false);
            selector = Selector.open();
            //实际上的Selector要和ServerSocketChannel进行绑定
            //让每一个连接上来的Socket都是默认ACCEPT状态
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器启动成功：" + this.port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            //轮询    死循环
            while (true) {
                int wait = this.selector.select();   //server.accept();
                if (wait == 0) {
                    continue;
                }
                Set<SelectionKey> keys = this.selector.selectedKeys();
                //对select中有多少个客户端已经连接上来了，但是还没有进行IO操作
                Iterator<SelectionKey> i = keys.iterator();
                while (i.hasNext()) {
                    SelectionKey key = i.next();
                    process(key);
                    i.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(SelectionKey key) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        if (key.isAcceptable()) {
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            //把状态改成read
            client.register(selector, SelectionKey.OP_READ);
        }
        else if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            //Buffer缓冲区的一个作用就体现了   先读到缓冲区中
            int len = client.read(buffer);
            if (len > 0) {
                buffer.flip();
                String content = new String(buffer.array(), 0, len);
                client.register(selector, SelectionKey.OP_WRITE);
                System.out.println(content);
            }
            buffer.clear();
        } else if (key.isWritable()) {
            SocketChannel client = (SocketChannel) key.channel();
            client.write(buffer.wrap("Hello Wold".getBytes()));
            client.close();
        }
    }
    public static void main(String[] args) {
        new NIOServer(8000).listen();
    }
}
