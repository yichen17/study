package com.yichen.agent.compare;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/2 16:14
 */
public class Demo {
    private int id;

    public Demo(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
//        return super.hashCode();
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
