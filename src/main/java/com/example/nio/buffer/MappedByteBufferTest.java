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
        // 只看名字就知道RandomAccessFile实现随机访问文件的功能，文档定义：随机存取文件的行为类似存储在文件系统中的一个大型字节数组。
        // 存在指向该隐含数组的光标或索引，称为文件指针；输入操作从文件指针开始读取字节，并随着对字节的读取而前移此文件指针。
        // 如果随机存取文件以读取/写入模式创建，则输出操作也可用；输出操作从文件指针开始写入字节，并随着对字节的写入而前移此文件指针。
        // 写入隐含数组的当前末尾之后的输出操作导致该数组扩展。该文件指针可以通过 getFilePointer 方法读取，并通过 seek 方法设置。
        // RandomAccessFile同时将FileInputStream和FileOutputStream整合到一起，而且支持将从文件任意字节处读或写数据，
        // RandomAccessFile类提供一种机制，相当于在文件流中插入了一个指针，可以按需读取。
        // File只是对一个文件或目录的抽象，可以想象成是一个文件句柄、标识，这个类本身只提供对文件的打开，关闭，删除，属性访问等等；
        RandomAccessFile randomAccessFile = new RandomAccessFile("01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte) 'Z');
        mappedByteBuffer.put(3, (byte) '9');
        randomAccessFile.close();
        System.out.println("修改成功");
    }
}
