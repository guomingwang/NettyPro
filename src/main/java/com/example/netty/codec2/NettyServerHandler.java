package com.example.netty.codec2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends SimpleChannelInboundHandler<MyDateInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyDateInfo.MyMessage myMessage) throws Exception {
        MyDateInfo.MyMessage.DateType dateType = myMessage.getDateType();
        if (dateType == MyDateInfo.MyMessage.DateType.StudentType) {
            MyDateInfo.Student student = myMessage.getStudent();
            System.out.println(student.getId() + student.getName());
        } else if (dateType == MyDateInfo.MyMessage.DateType.WorkerType) {
            MyDateInfo.Worker worker = myMessage.getWorker();
            System.out.println(worker.getAge() + worker.getName());
        } else {
            System.out.println("传输的类型不正确");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端~φ(゜▽゜*)♪", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
