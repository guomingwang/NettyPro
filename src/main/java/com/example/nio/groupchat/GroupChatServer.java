package com.example.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {

    private static final int PORT = 6667;

    private Selector selector;
    private ServerSocketChannel listenChannel;

    public GroupChatServer() {
        try {
            // 创建一个selector
            selector = Selector.open();
            // 创建一个ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            // ServerSocketChannel绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // ServerSocketChannel设置非阻塞
            listenChannel.configureBlocking(false);
            // ServerSocketChannel注册到Selector，并指定用于监听连接事件
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        try {
            // 开始循环监听事件
            while (true) {
                int count = selector.select(2000);
                if (count > 0) {
                    // 获取待处理事件的SelectionKey
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    // 遍历每一个SelectionKey
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        // 如果是连接事件
                        if (key.isAcceptable()) {
                            // 建立连接并获取客户端socketChannel
                            SocketChannel sc = listenChannel.accept();
                            // 将客户端SocketChannel配置为非阻塞
                            sc.configureBlocking(false);
                            // 将客户端SocketChannel注册到Selector，并指定监听某个事件
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + " 上线 ");
                        }
                        // 如果是读事件
                        if (key.isReadable()) {
                            readDate(key);
                        }
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDate(SelectionKey key) {
        SocketChannel channel = null;
        try {
            // 获取客户端socketChannel
            channel = (SocketChannel) key.channel();
            // 从客户端socketChannel读取
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if (count > 0) {
                String msg = new String(buffer.array());
                System.out.println("from 客户端：" + msg);
                // 向其他客户端转发消息，排除自己
                sendInfoToOtherClient(msg, channel);
            }

        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了...");
                // 取消注册
                key.cancel();
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void sendInfoToOtherClient(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器消息转发中...");
        // 将数据写入到除了消息发送方的其它socketChannel
        for (SelectionKey key :
                selector.keys()) {
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self) {
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
