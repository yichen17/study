package com.jf.pre.third.gateway.entity;

/**
 * @author liumg
 * @version 创建时间：2019/7/10.
 */
public class GatewayWhiteIpEntity {

    /**
     * 商户唯一id
     */
    String appId;

    /**
     * ip正则表达式
     */
    String ip;

    /**
     * 是否启用状态，0：标识启用，-1：初始状态， 1：禁用
     */
    int status;

    /**
     * 业务类型
     */
    String product;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
