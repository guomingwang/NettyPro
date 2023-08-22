package com.example.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result; // 返回的结果
    private String para; // 方法调用入参

    // 调用顺序：2
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("----调用 NettyClientHandler channelActive");
        context = ctx;
    }

    // 调用顺序：4
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("----调用 NettyClientHandler channelRead");
        result = msg.toString();
        // 唤醒等待的线程
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    // 调用顺序：3、5
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("----调用 NettyClientHandler call 1");
        context.writeAndFlush(para);
        // 线程等待
        wait();
        System.out.println("----调用 NettyClientHandler call 2");
        return result;
    }

    // 调用顺序：1
    public void setPara(String para) {
        System.out.println("----调用 NettyClientHandler setPara");
        this.para = para;
    }
}
