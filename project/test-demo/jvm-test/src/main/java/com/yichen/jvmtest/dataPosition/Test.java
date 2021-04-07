package com.yichen.jvmtest.dataPosition;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/1 17:05
 */
public class Test {
    public static void main(String[] args)throws Exception {
        Person person=new Person("yichen",17);
        Person person1=(Person)person.clone();
        System.out.println(person==person1);
        Person person2=person;
        person.setName("banyu");
        person.setAge(18);
        System.out.println(person==person1);
        System.out.println(person==person2);
        System.out.println(person);
        System.out.println(person1);
        System.out.println(person2);
    }
//    怎么信息网络定位，需要依靠mac 地址吗
}
