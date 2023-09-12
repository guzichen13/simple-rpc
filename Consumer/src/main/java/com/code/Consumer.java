package com.code;

import com.code.common.Invocation;
import com.code.protocol.HttpClient;
import com.code.proxy.ProxyFactory;

public class Consumer {
    public static void main(String[] args) {
         HelloService helloService = ProxyFactory.getProxy(HelloService.class);
         String result = helloService.sayHello("张三123321");
         System.out.println(result);

        // // 构建invocation对象
        // Invocation invocation = new Invocation(HelloService.class.getName(), "sayHello",
        //        new Class[]{String.class}, new Object[]{"zhangsan"});
        //
        // // 发送网络请求
        // HttpClient httpClient = new HttpClient();
        // String result = httpClient.send("localhost", 8080, invocation);
        // System.out.println(result);


    }
}
