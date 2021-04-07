package com.yichen.jvmtest.dataPosition;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/1 17:04
 */
public class Person {
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Person person=new Person();
        person.setName(this.name);
        person.setAge(this.age);
        return person;
    }
}
