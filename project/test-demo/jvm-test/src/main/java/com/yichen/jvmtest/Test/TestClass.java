package com.yichen.jvmtest.Test;

import sun.misc.Unsafe;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.function.Consumer;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/15 11:28
 */

public class TestClass {
    public static void main(String[] args) throws Exception{
        Consumer<String> consumer=s-> System.out.println(s);
        consumer.accept("lambda");
//        String filePath = "D:\\rocketmq\\test-demo\\jvm-test\\target\\classes\\com\\yichen\\jvmtest\\Test\\TestClass.class";
//        byte[] buffer =filePath.getBytes();
//        reflectGetUnsafe().defineAnonymousClass(TestClass.class,buffer,null);
        System.out.println("hello");
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
