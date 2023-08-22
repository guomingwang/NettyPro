package com.example.netty.codec2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int random = new Random().nextInt(3);
        MyDateInfo.MyMessage myMessage = null;
        if (random == 0) {
            myMessage = MyDateInfo.MyMessage.newBuilder()
                    .setDateType(MyDateInfo.MyMessage.DateType.StudentType)
                    .setStudent(MyDateInfo.Student.newBuilder().setId(5).setName("玉麒麟 卢俊义").build())
                    .build();
        } else {
            myMessage = MyDateInfo.MyMessage.newBuilder()
                    .setDateType(MyDateInfo.MyMessage.DateType.WorkerType)
                    .setWorker(MyDateInfo.Worker.newBuilder().setAge(20).setName("老李").build())
                    .build();
        }
        ctx.writeAndFlush(myMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
