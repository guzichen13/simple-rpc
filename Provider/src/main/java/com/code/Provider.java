package com.code;

import com.code.common.URL;
import com.code.protocol.HttpServer;
import com.code.register.LocalRegister;
import com.code.register.MapRemoteRegister;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Provider {
    public static void main(String[] args) {

        // 本地注册
        LocalRegister.register(HelloService.class.getName(), "1.0", HelloServiceImpl.class);
        LocalRegister.register(HelloService.class.getName(), "2.0", HelloServiceImpl2.class);

        // 远程注册中心注册 服务注册
        URL url = null;
        try {
            url = new URL(InetAddress.getLocalHost().getHostAddress(), 8080);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        MapRemoteRegister.register(HelloService.class.getName(), url);

        // 接收网络请求 tomcat / netty
        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostname(), url.getPort());

    }
}
