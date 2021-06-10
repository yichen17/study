package com.yichen.reflect.base;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/6/10 10:08
 *
 */
public class Student {

    private String name;
    private long id;

    public Student(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
