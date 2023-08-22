package com.example.nio.zerocopy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {

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
        FileOutputStream fileOutputStream = new FileOutputStream("03.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();
        // transferFrom
        fileChannel02.transferFrom(fileChannel01, 0, fileChannel01.size());

        fileChannel01.close();
        fileChannel02.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
