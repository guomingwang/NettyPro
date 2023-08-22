package com.example.netty.dubborpc.provider;

import com.example.netty.dubborpc.netty.NettyServer;

public class ServerBootstrap {

    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1", 8080);
    }
}
