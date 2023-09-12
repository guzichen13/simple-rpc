package com.code;

import com.code.protocol.HttpServer;
import com.code.register.LocalRegister;

public class Provider {
    public static void main(String[] args) {

        LocalRegister.register(HelloService.class.getName(), "1.0", HelloServiceImpl.class);
        LocalRegister.register(HelloService.class.getName(), "2.0", HelloServiceImpl2.class);

        // 接收网络请求 tomcat / netty
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);
    }
}
