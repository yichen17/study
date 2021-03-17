package com.jf.pre.third.gateway.exception.v1;

/**
 * @author liumg
 * @version 创建时间：2019/7/17.
 */
public enum GatewayV1ExceptionCode {

    /**
     * 系统内部异常
     */
    SERVICE_INTERNAL_ERROR("err9999", "系统异常"),

    /**
     * 服务访问权限受限
     */
    AUTH_ACCESS_DENY("err1008", "该软件没有该接口的权限"),

    /**
     * 服务访问网络受限
     */
    AUTH_ACCESS_IP_DENY("err1009", "无效IP"),

    /**
     * 服务访问网络受限
     */
    RATE_EXCEED_LIMIT("err1009", "无效IP");

    private String value;

    private String desc;

    private GatewayV1ExceptionCode(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
