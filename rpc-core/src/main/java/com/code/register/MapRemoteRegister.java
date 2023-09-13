package com.code.register;

import com.code.common.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 远程注册
 */
public class MapRemoteRegister {
    private static final Map<String, List<URL>> map = new HashMap<>();
    public static void register(String interfaceName, URL url) {
        List<URL> list = null;
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(url);
        map.put(interfaceName, list);
    }

    public static List<URL> get(String interfaceName) {
        return map.get(interfaceName);
    }

}