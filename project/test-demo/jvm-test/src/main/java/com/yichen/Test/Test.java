package com.yichen.Test;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/25 16:07
 */
public class Test {
    public static void main(String[] args) {
        Child child=new Child();
        System.out.println(child.Print());
        TestOrder order=new TestOrder("yichen",18,"男");
        System.out.println(order);
    }
}
