package com.yichen.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/12 11:17
 * @describe  new String 创建的对象   ==  和 equals 方法耗时差异  理论上来说应该 == 耗时更短
 */
public class TestEqualCostTime {
    public static void main(String[] args) {
        List<String> a=new ArrayList<>(4096);
        List<String> b=new ArrayList<>(4096);
        for(int i=0;i<4096;i++){
            a.add(""+(int)(Math.random()*i+10));
            b.add(""+(int)(Math.random()*i+10));
        }
        int count=0;
        long start=System.currentTimeMillis();
        for(int i=0;i<4096;i++){
            for(int j=0;j<4096;j++){
                count+=a.get(i)==b.get(j)?1:0;
            }
        }
        long stop=System.currentTimeMillis();
        System.out.println("== cost time: "+(stop-start));
        start=System.currentTimeMillis();
        for(int i=0;i<4096;i++){
            for(int j=0;j<4096;j++){
                count+=a.get(i).equals(b.get(j))?1:0;
            }
        }
        stop=System.currentTimeMillis();
        System.out.println("equals cost time: "+(stop-start));
    }
}
