package com.example.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {

    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(10);
        for (int i = 0; i < 10; i++) {
            buf.writeByte(i);
        }
        System.out.println("capacity = " + buf.capacity());
        // 输出，不需要flip（底层维护了readerIndex、writerIndex）
        for (int i = 0; i < buf.capacity(); i++) {
//            System.out.println(buf.getByte(i));
            System.out.println(buf.readByte());
        }
    }
}
