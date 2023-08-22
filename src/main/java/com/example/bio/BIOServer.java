package com.example.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {

    public static void main(String[] args) throws IOException {
        // 创建一个线程池：核心线程数0，非核心线程数Integer的最大值，空闲等待时间60s
        // return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
        //                                      60L, TimeUnit.SECONDS,
        //                                      new SynchronousQueue<Runnable>());
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        // 创建一个ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        // 循环监听连接
        while (true) {
            // 主线程
            System.out.println("主线程线程信息 id=" + Thread.currentThread().getId() + "名字=" + Thread.currentThread().getName());
            System.out.println("等待连接...");
            // 获取建立连接的客户端socket
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端");
            // 从线程池中执行一个线程任务
            newCachedThreadPool.execute(() -> handler(socket));
        }
    }

    private static void handler(Socket socket) {
        try {
            System.out.println("子线程线程信息 id=" + Thread.currentThread().getId() + "名字=" + Thread.currentThread().getName());
            // 从客户端socket获取输入流
            InputStream inputStream = socket.getInputStream();
            // 从输入流读取数据并打印（最终目的）
            byte[] bytes = new byte[1024];
            while (true) {
                System.out.println("read...");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read, StandardCharsets.UTF_8));
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client的连接");
            try {
                // 关闭客户端socket
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
