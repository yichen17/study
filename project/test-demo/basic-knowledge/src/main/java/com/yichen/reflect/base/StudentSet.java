package com.yichen.reflect.base;

import java.util.AbstractSet;
import java.util.Iterator;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/6/10 11:11
 */
public class StudentSet extends AbstractSet<Student> {

    Student[] keys;
    int size;

    public StudentSet() {
        this.keys = new Student[16];
    }

    @Override
    public Iterator<Student> iterator() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }
}
