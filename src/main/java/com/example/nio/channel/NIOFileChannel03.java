package com.example.nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {

    /**
     * 文件拷贝：读文件->写文件
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // file->channel->buffer->channel->file
        // channel01
        FileInputStream fileInputStream = new FileInputStream("01.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();
        // channel02
        FileOutputStream fileOutputStream = new FileOutputStream("02.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();
        // buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) {
            //        position = 0;
            //        limit = capacity;
            //        mark = -1;
            byteBuffer.clear();
            int read = fileChannel01.read(byteBuffer);
            if (read == -1) {
                break;
            }
            //        position = 0;
            //        limit = position;
            //        mark = -1;
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
