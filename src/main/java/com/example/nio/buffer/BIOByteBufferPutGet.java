package com.example.nio.buffer;

import java.nio.ByteBuffer;

public class BIOByteBufferPutGet {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putInt(100);
        buffer.putLong(9);
        buffer.putChar('尚');
        buffer.putShort((short) 4);
        buffer.flip();
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getLong());
    }
}
