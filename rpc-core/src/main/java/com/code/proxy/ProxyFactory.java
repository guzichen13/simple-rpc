package com.code.proxy;

import com.code.common.Invocation;
import com.code.common.URL;
import com.code.loadbalance.Loadbalance;
import com.code.protocol.HttpClient;
import com.code.register.MapRemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ProxyFactory {
    public static<T> T getProxy(Class interfaceClass) {
        // 用户配置
        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                String mock = System.getProperty("mock");
                if (mock != null || mock.startsWith("return:")) {
                    String result = mock.replace("return:", "");
                    return result;
                }

                // 构建invocation对象
                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(),
                        method.getParameterTypes(), args);

                // 发送网络请求
                HttpClient httpClient = new HttpClient();

                // 服务发现
                List<URL> list = MapRemoteRegister.get(interfaceClass.getName());

                // 服务调用
                String result = null;

                List<URL> invokedUrls = new ArrayList<>();

                int max = 3;
                while (max > 0) {

                    // 负载均衡
                    list.remove(invokedUrls);
                    URL url = Loadbalance.random(list);
                    invokedUrls.add(url);

                    try {
                        result = httpClient.send(url.getHostname(), url.getPort(), invocation);
                    } catch (Exception e) {
                        if (max-- != 0) continue;
                        // 容错
                        // error-callback = com.code.HelloServiceErrorCallback
                        return "服务调用异常";
                    }
                }

                return result;
            }
        });
        return (T) proxyInstance;
    }
}
