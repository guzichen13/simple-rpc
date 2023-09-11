package com.code;

public class Consumer {
    public static void main(String[] args) {
        HelloService helloService;
        String result = helloService.sayHello("张三");
        System.out.println(result);

    }
}
