package com.yichen.useall.bean;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/16 14:13
 * @describe  查询手机号 存储日志
 */
public class PhoneLog {
    private String date;
    private int id;
    private String phone;
    private String result;
    private int state;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
