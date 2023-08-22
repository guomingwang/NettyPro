package com.example.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NettyByteBuf02 {

    public static void main(String[] args) {
        ByteBuf buf = Unpooled.copiedBuffer("hello, word!北京", CharsetUtil.UTF_8);
        if (buf.hasArray()) {
            byte[] content = buf.array();
            System.out.println(new String(content, CharsetUtil.UTF_8));
            System.out.println("buf = " + buf);
            System.out.println(buf.arrayOffset());
            System.out.println(buf.readerIndex());
            System.out.println(buf.writerIndex());
            System.out.println(buf.readableBytes());
            System.out.println(buf.getCharSequence(0, 4, CharsetUtil.UTF_8));
            System.out.println(buf.getCharSequence(4, 6, CharsetUtil.UTF_8));
        }
    }
}
