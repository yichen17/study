package com.yichen.jvmtest.model;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/25 16:47
 */
public class Student {
    private String name;
    private Integer age;
    private String certId;

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

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public Student(String name, Integer age, String certId) {
        this.name = name;
        this.age = age;
        this.certId = certId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", certId='" + certId + '\'' +
                '}';
    }
}
