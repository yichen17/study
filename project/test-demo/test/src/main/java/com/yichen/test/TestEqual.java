package com.yichen.test;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/12 11:40
 * @describe   测试  == 底层走的流程
 */
public class TestEqual {
    public static void main(String[] args) {
        String a=new String("aa");
        String b=new String("bb");
        System.out.println(a==b);
    }
}
