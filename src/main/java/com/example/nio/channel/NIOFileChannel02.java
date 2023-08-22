package com.example.nio.channel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {

    /**
     * 写文件
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // buffer->channel->file
        // buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        String str = "hello,尚硅谷！hello,尚硅谷！hello,尚硅谷！hello,尚硅谷！hello,尚硅谷！";
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        // channel
        FileOutputStream fileOutputStream = new FileOutputStream("01.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
