package com.javaniuniu.tank2;

import java.io.IOException;
import java.util.Properties;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 1:24 AM
 */
public class PropertyMgr {
    static Properties props = new Properties();

    static {
        try {
            props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("./config.property"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object get(String key) {
        if (props == null) return null;
        return props.get(key);
    }

    public static void main(String[] args) {
        System.out.println(get("initTankCount"));
    }
}
