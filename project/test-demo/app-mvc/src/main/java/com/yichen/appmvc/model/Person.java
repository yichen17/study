package com.yichen.appmvc.model;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/7/28 15:45
 * @describe
 */
public class Person {
    private String customerId;
    private String appId;

    @Override
    public String toString() {
        return "Person{" +
                "customerId='" + customerId + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
