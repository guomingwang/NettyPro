package com.example.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class NettyServer {

    public static void main(String[] args) {
        // 创建一个bossGroup
        EventLoopGroup bossGroup = null;
        // 创建一个workerGroup
        EventLoopGroup workerGroup = null;
        try {
            // 默认线程数：cpu核数*2
            bossGroup = new NioEventLoopGroup(1);
            workerGroup = new NioEventLoopGroup(8);
            // 创建一个ServerBootstrap
            ServerBootstrap bootstrap = new ServerBootstrap();
            // ServerBootstrap配置bossGroup、workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    // ServerBootstrap配置NioServerSocketChannel类型
                    .channel(NioServerSocketChannel.class)
                    // ServerBootstrap添加一些Channel配置
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // .handler(null) // handler 对应 bossGroup；childHandler 对应 workerGroup
                    // ServerBootstrap添加Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个管道测试对象
                        // 给pipeline设置处理器
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("客户 SocketChannel hashcode = " + ch.hashCode());
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });  // 给workerGroup的EventLoop对应的管道设置处理器
            System.out.println("...服务器 is ready...");
            // 启动服务器（并绑定端口）
            ChannelFuture cf = bootstrap.bind(6668).sync();
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (cf.isSuccess()) {
                        System.out.println("监听端口 6668 成功");
                    } else {
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });
            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
