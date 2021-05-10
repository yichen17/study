package com.yichen.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/14 16:12
 * @describe 测试 在 list数组中 在使用迭代器遍历的时候删除满足的条件时，是否会影响原有数组
 *              结论，对象是同一个
 */
public class ListIteratorTest {
    public static void main(String[] args) {
//        String [] data={"hello","haha","shanliang","world"};
//        List<String> list= Arrays.asList(data);


        List<String> list=new ArrayList<>();
        list.add("shanliang");
        list.add("haha");
        list.add("banyu");

        List<String> result=removePublic(list);

        list.stream().forEach(System.out::println);
        result.stream().forEach(System.out::println);
        System.out.println(list==result);
    }
    public static List<String> removePublic(List<String> data){
        Iterator it=  data.iterator();
        while(it.hasNext()){
            String value= (String) it.next();
            if("haha"==value){
                it.remove();
            }
        }
        return data;
    }
}
