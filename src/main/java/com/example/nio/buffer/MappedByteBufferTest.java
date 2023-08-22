package com.example.nio.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {

    /**
     * 文件直接在堆外内存修改，不需要拷贝一次
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'Z');
        mappedByteBuffer.put(3, (byte) '9');
        randomAccessFile.close();
        System.out.println("修改成功");
    }
}
