package com.example.netty.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class NettyClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = null;
        try {
            // 创建一个group
            group = new NioEventLoopGroup();
            // 创建一个Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            // - Bootstrap配置group
            bootstrap.group(group)
                    // - Bootstrap配置NioSocketChannel类型
                    .channel(NioSocketChannel.class)
                    // - Bootstrap往ChannelPipeline中添加ChannelHandler
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("encoder", new ProtobufEncoder());
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端 ok...");
            // - Bootstrap绑定远程地址并使用ChannelFuture进行监听
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            // - 使用ChannelFuture监听关闭通道事件
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
