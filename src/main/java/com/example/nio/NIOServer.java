package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) throws IOException {
        // 创建一个selector
        Selector selector = Selector.open();
        // 创建一个ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // ServerSocketChannel绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // ServerSocketChannel设置非阻塞
        serverSocketChannel.configureBlocking(false);
        // ServerSocketChannel注册到Selector，并指定用于监听连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("注册后的selectionKey数量=" + selector.keys().size());
        // 循环监听事件
        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            // 获取待处理事件的SelectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("有事件发生的 selectionKeys数量= " + selectionKeys.size() );
            // 遍历每一个SelectionKey
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                // 如果是连接事件
                if (key.isAcceptable()) {
                    // 建立连接并获取客户端socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成了一个 socketChannel " + socketChannel.hashCode());
                    // 将客户端SocketChannel配置为非阻塞
                    socketChannel.configureBlocking(false);
                    // 将客户端SocketChannel注册到Selector，并指定监听某个事件
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("客户端连接后，注册后的selectionKey数量=" + selector.keys().size());
                }
                // 如果是读事件
                if (key.isReadable()) {
                    // 获取客户端socketChannel
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 取到一个空对象
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    // 从客户端socketChannel读取数据并打印（最终目的）
                    channel.read(buffer);
                    System.out.println("from 客户端 " + new String(buffer.array()));
                }
                // 移除SelectionKey
                keyIterator.remove();
            }

        }
    }
}
