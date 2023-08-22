package com.example.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChatClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6667;
    private String username;

    private Selector selector;
    private SocketChannel socketChannel;

    public GroupChatClient() throws IOException {
        // 创建一个客户端socketChannel
        selector = Selector.open();
        // socketChannel与远程地址建立连接
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        // socketChannel设置为非阻塞
        socketChannel.configureBlocking(false);
        // SocketChannel注册到Selector，并指定用于监听读事件
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok...");
    }

    public void sendInfo(String info) {
        info = username + " 说：" + info;
        try {
            // 扫描流读取键盘输入并且写入到socketChannel
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo() {
        try {
            int readChannel = selector.select();
            if (readChannel > 0) {
                // 获取待处理事件的SelectionKey
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 如果是读事件
                    if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        sc.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }
                    // 移除SelectionKey
                    iterator.remove();
                }
            } else {
//                System.out.println("没有可以用的通道...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatClient chatClient = new GroupChatClient();
        // 主线程创建一个扫描流
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String s = scanner.nextLine();
            chatClient.sendInfo(s);
        }
        // 创建一个子线程
        new Thread(() -> {
            while (true) {
                chatClient.readInfo();
                try {
                    Thread.currentThread().sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
