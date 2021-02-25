package com.yichen.test;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/25 14:06
 */
public class Test {
    public static void main(String[] args)throws Exception {
        TestClassConstructOrder test=new TestClassConstructOrder();
        System.out.println("===============");
//        TestClassConstructOrder.Inner2 inner2=new TestClassConstructOrder.Inner2();
//        TestClassConstructOrder.Inner1 inner1=new TestClassConstructOrder.Inner1();

//        Class<?> clazz1=TestClassConstructOrder.Inner1.class;
//        Object inner1=clazz1.getConstructor().newInstance();
//        Class<?> clazz2=TestClassConstructOrder.Inner2.class;
//        Object inner2=clazz2.getConstructor().newInstance();

        Class<? extends  TestClassConstructOrder.Inner1> clazz3=test.inner1.getClass();
        TestClassConstructOrder.Inner1 inner3=clazz3.newInstance();

    }
}
