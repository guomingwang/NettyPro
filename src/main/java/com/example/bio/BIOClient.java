package com.example.bio;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author WangGuoming
 * created on 2019/2/12
 */
public class BIOClient {

    public static void main(String[] args) throws IOException {
        // Socket
        Socket socket = new Socket("127.0.0.1", 6666);
        // 输出流
        OutputStream outputStream = socket.getOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        PrintStream printStream = new PrintStream(bufferedOutputStream, true, "UTF-8");
        // 输入流
        Scanner scanner = new Scanner(System.in);
        while (true) {
            while (scanner.hasNextLine()) {
                printStream.println(scanner.nextLine());
            }
        }
    }
}
