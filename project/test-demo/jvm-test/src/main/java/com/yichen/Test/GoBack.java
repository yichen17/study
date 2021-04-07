package com.yichen.Test;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/27 16:09
 */
public class GoBack {
    public static void main(String[] args) {
        int i=99;
        method1(i);
    }
    public static void method1(int i){
        System.out.println("method1"+i);
        method2(i);
    }
    public static void method2(int j){
        j++;
        System.out.println("method2"+j);
    }
}
