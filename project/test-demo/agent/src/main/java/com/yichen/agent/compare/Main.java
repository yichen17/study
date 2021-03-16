package com.yichen.agent.compare;

import java.util.HashMap;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/2 16:10
 * @describe  通常情况下 == 与 equals 本质
 */
public class Main {
    public static void main(String[] args) {
        String a=new String("11");
        String b=new String("11");
        System.out.println(a==b);
        System.out.println(a.equals(b));
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println("=============分割线======================");
        Demo demo1=new Demo(1);
        Demo demo2=new Demo(2);
        System.out.println(demo1==demo2);
        System.out.println(demo1.hashCode());
        System.out.println(demo2.hashCode());
        System.out.println("--------------------分割线---------------------------");
        HashMap<Object,Object> map=new HashMap<>();
        map.put(demo1,"1"   );
        map.put(demo2,"2"   );
        System.out.println(map.get(demo1));
    }
}
