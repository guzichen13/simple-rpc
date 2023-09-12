package com.code;

import com.code.protocol.HttpServer;

public class Provider {
    public static void main(String[] args) {
        // 接收网络请求 tomcat / netty
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);
    }
}
