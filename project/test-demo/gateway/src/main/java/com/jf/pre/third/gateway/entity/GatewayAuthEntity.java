package com.jf.pre.third.gateway.entity;

/**
 * 鉴权信息
 * @author liumg
 * @version 创建时间：2019/7/10.
 */
public class GatewayAuthEntity {

    /**
     * 商户唯一id
     */
    String appId;

    /**
     * 商户token值
     */
    String token;

    /**
     * 商户状态
     */
    int status;

    /**
     * 产品类型
     */
    String product;

    /**
     * 自增id
     */
    int id;

    /**
     *
     */
    String remark;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
