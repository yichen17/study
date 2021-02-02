package com.yichen.Annotation.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/20 9:49
 */
public class TestX {

    public static void main(String[] args)throws Exception {
        Class<ChildX> clazz=ChildX.class;
        System.out.println("----对类进行调试");
        if (clazz.isAnnotationPresent(InheritedTest.class)){
            System.out.println(clazz.getAnnotation(InheritedTest.class).value());
        }
        System.out.println("----对方法method调用");
        Method method=clazz.getMethod("method",null);
        if(method.isAnnotationPresent(InheritedTest.class)){
            System.out.println(method.getAnnotation(InheritedTest.class).value());
        }
        System.out.println("----对方法method2调用");
        Method method2=clazz.getMethod("method2",null);
        if (method2.isAnnotationPresent(InheritedTest.class)) {
            System.out.println(method2.getAnnotation(InheritedTest.class).value());
        }
        System.out.println("----对属性field调用");
        Field field=clazz.getField("a");
        if(field.isAnnotationPresent(InheritedTest.class)){
            System.out.println(field.getAnnotation(InheritedTest.class).value());
        }
    }

}
