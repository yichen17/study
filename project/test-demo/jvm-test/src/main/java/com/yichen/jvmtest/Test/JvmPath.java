package com.yichen.jvmtest.Test;

import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/15 10:15
 * @describe  获取jvm 三个类路径
 * bootstrap class、  extension classes 、user class
 */
public class JvmPath {

    public static void main(String[] args) {
        String bootPath=System.getProperty("sun.boot.class.path");
        String extPath=System.getProperty("java.ext.dirs");
        String userPath=System.getProperty("user.dir");
        System.out.println(bootPath);
        System.out.println(extPath);
        System.out.println(userPath);

//        System.out.println(reflectGetUnsafe());
        System.out.println(reflectGetUnsafe().addressSize()+"|"+reflectGetUnsafe().pageSize());

    }
    private static Unsafe reflectGetUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
