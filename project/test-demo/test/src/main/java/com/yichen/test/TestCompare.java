package com.yichen.test;

import java.util.HashSet;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/28 16:14
 * @describe  测试数值比较通过 = 和 异或(位运算) 哪个快
 */
public class TestCompare {

    public static void main(String[] args) {
        long start,end;
        int value=2;
        start=System.currentTimeMillis();
        for(int i=0;i<100000000;i++){
            if(value==2){

            }
        }
        end=System.currentTimeMillis();
        System.out.println(end-start);
        // compare by xor
        start=System.currentTimeMillis();
        for(int i=0;i<100000000;i++){
            if((value^2)==0){

            }
        }
        end=System.currentTimeMillis();
        System.out.println(end-start);

        HashSet<Integer> set=new HashSet<>();


    }

}
