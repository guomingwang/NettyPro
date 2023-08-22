package com.example.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

    public static void main(String[] args) throws IOException {
        String fileName = "eSupport.zip";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));

        long startTime = System.currentTimeMillis();
        // linux系统下，一次传整个文件
        // windows系统下，一次传8m，需要分段传输文件
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        long endTime = System.currentTimeMillis();
        System.out.println("发送的总字节数= " + transferCount  + ", 耗时=" + (endTime - startTime ));
        fileChannel.close();
    }
}
