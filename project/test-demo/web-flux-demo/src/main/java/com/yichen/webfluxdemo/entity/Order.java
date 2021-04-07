package com.yichen.webfluxdemo.entity;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/7 14:36
 */
public class Order {
    private String id;
    private String time;

    public Order() {
    }

    public Order(String id, String time) {
        this.id = id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
