package com.yichen.Test;

import com.yichen.jvmtest.model.Student;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/2 17:56
 * @Describe 测试 静态属性，类属性和构造方法的先后顺序
 */
public class TestOrder {
    private String name;
    private int age=10;
    private boolean state=true;
    private Student student=new Student();

    public String sex;

    static{
        System.out.println("test order has start-up");
    }

    private static String POSITION="test position";


    public TestOrder(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "TestOrder{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", state=" + state +
                ", student=" + student +
                ", sex='" + sex + '\'' +
                '}';
    }
}
