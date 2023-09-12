package com.code.protocol;

import com.code.common.Invocation;
import com.code.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HttpServerHandler {

    public void handler(HttpServletRequest req, HttpServletResponse resp) {
        // 处理请求 接口 方法 方法参数
        try {
            // 解析request 拿到 接口 方法 方法参数
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            // 拿到接口名
            String interfaceName = invocation.getInterfaceName();
            // 通过本地注册获取实现类
            Class classImpl = LocalRegister.get(interfaceName, "1.0");
            // 获取实现类的方法
            Method method = classImpl.getMethod(invocation.getMethodName(), invocation.getParameterType());
            // 通过反射执行方法
            String result = (String) method.invoke(classImpl.newInstance(), invocation.getParameters());

            // 结果写入
            IOUtils.write(result, resp.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
