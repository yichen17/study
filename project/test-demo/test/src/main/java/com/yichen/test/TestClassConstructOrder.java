package com.yichen.test;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/25 14:04
 * @describe 测试类内部包含类时，两个类的构造顺序问题
 */
public class TestClassConstructOrder {
//    private Inner2 inner2=new Inner2();
    public Inner1 inner1=new Inner1();
    static {
        System.out.println("there is static code");
    }
    public TestClassConstructOrder() {
        System.out.println("outer construct");
    }
    class Inner1{
        public void show(){
            System.out.println("inner1 show");
        }
        public Inner1() {
            System.out.println("inner1 construct");
        }
    }
    static class Inner2{
        public void show(){
            System.out.println("inner2 show");
        }
        public Inner2() {
            System.out.println("inner2 construct");
        }
    }
}


