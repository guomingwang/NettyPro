package com.example.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("服务器接收到信息如下：");
        System.out.println("长度=" + len);
        System.out.println("内容=" + new String(content, CharsetUtil.UTF_8));
        System.out.println("服务器接收到消息包数量="+ (++this.count));
        System.out.println("--------------------------------");
        byte[] responseContent = UUID.randomUUID().toString().getBytes(CharsetUtil.UTF_8);
        int length = content.length;
        MessageProtocol messageProtocol = new MessageProtocol(length, responseContent);
        ctx.writeAndFlush(messageProtocol);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
