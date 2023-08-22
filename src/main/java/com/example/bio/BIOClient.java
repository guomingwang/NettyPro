package com.example.bio;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author WangGuoming
 * created on 2019/2/12
 */
public class BIOClient {

    public static void main(String[] args) throws IOException {
        // 创建一个客户端Socket
        Socket socket = new Socket("127.0.0.1", 6666);
        // 从客户端socket获取输出流
        OutputStream outputStream = socket.getOutputStream();
        // 创建一个扫描流
        Scanner scanner = new Scanner(System.in);
        // 扫描流循环读取键盘输入数据，填充到输出流
        while (true) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                outputStream.write(line.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
