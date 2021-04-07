package com.jf.pre.third.gateway.exception;

/**
 * @author liumg
 * @version 创建时间：2019/7/17.
 */
public enum GatewayExceptionCode {

    /**
     * 系统内部异常
     */
    SERVICE_INTERNAL_ERROR("ERR-1001-001", "系统内部异常5xx"),

    /**
     * 服务不存在
     */
    SERVICE_NOT_FOUND("ERR-1001-002", "请求服务不存在404"),

    /**
     * 服务访问超频
     */
    SERVICE_ACCESS_LIMIT_EXCEED("ERR-1001-003", "服务访问超频"),

    /**
     * 鉴权信息格式不正确
     */
    AUTH_ACCESS_INVALID("INFO-1001-001", "服务访问鉴权信息格式不正确"),

    /**
     * 服务访问权限受限
     */
    AUTH_ACCESS_DENY("INFO-1001-002", "服务访问权限受限，请检查配置信息"),

    /**
     *
     */
    ACCESS_REPEAT_DENY("INFO-10001-003", "服务端反复提交同一任务"),

    /**
     * 服务访问网络受限
     */
    AUTH_ACCESS_IP_DENY("INFO-1001-003", "服务访问网络受限，请配置网络白名单");

    private String value;

    private String desc;

    private GatewayExceptionCode(String value, String desc) {
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
