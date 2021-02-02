package com.yichen.jvmtest.Test;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/18 14:25
 */
public class ByteCodeDemo {
    private int a=1;
    public int add(){
        int b=2;
        int c=a+b;
        System.out.println(c);
        return c;
    }
}
