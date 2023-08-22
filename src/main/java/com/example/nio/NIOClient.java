package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

    public static void main(String[] args) throws IOException {
        // 创建一个客户端socketChannel
        SocketChannel socketChannel = SocketChannel.open();
        // socketChannel设置为非阻塞
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // socketChannel与远程地址建立连接
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作...");
            }
        }
        String str = "hello，尚硅谷~";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        // 将数据写入socketChannel中
        socketChannel.write(buffer);
        // 没啥用，用来阻塞线程
        System.in.read();
    }
}
