package com.example.nio.buffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ScatteringAndGatheringTest {

    // telnet 127.0.0.1 7000
    // CTRL+]
    // send 12345678901234567890
    public static void main(String[] args) throws IOException {

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        int messageLength = 8;

        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(inetSocketAddress);
        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true) {
            int byteRead = 0;
            while (byteRead < messageLength) {
                socketChannel.read(byteBuffers);
                byteRead += 1;
                System.out.println("byteRead=" + byteRead);
                Arrays.asList(byteBuffers).stream().map(buffer -> "position=" + buffer.position()
                        + ", limit=" + buffer.limit()).forEach(System.out::println);
            }
            Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());

            long byteWrite = 0;
            while (byteWrite < messageLength) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += 1;
            }
            Arrays.asList(byteBuffers).forEach(buffer -> {
                //         position = 0;
                //        limit = capacity;
                //        mark = -1;
                buffer.clear();
            });
            System.out.println("byteRead=" + byteRead + "byteWrite=" + byteWrite
            + ", messageLength=" + messageLength);
        }
    }
}
