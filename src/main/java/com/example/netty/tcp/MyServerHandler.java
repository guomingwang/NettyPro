package com.example.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buf = new byte[msg.readableBytes()];
        msg.readBytes(buf);
        String message = new String(buf, CharsetUtil.UTF_8);
        System.out.println("服务器接收到数据 " + message);
        System.out.println("服务器接收到信息量=" + (++this.count));

        ByteBuf responseBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString() + "\n", CharsetUtil.UTF_8);
        ctx.writeAndFlush(responseBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
