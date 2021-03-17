package com.jf.pre.third.gateway.constants;

/**
 * @author liumg
 * @version 创建时间：2019/7/15.
 */
public class GatewayConstants {

    /**
     * 用于记录请求开始时的时间
     */
    public final static String LOGGING_START_TIME = "request-start-time";

    /**
     * 用于记录后端请求真实的路由信息
     */
    public final static String SERVICE_ROUTE = "service-request-route";

    /**
     * 用于记录后端调用的流水号
     */
    public final static String SERVICE_REQUEST_ID = "service-request-id";

    /**
     * 回调大数据专用
     */
    public final static String SERVICE_REQUEST_EXT_DATA = "service-request-ext-data";

    /**
     * 请求后端的服务名
     */
    public final static String SERVICE_REQUEST_NAME = "service-name";

    /**
     * 租户唯一id
     */
    public final static String TENANT_ID = "tenant-id";

    /**
     * 路由后端真实状态信息
     */
    public final static String SERVICE_REQUEST_ROUTE_STATUS = "service-request-route-status";

    /**
     * 路由后端真实付费情况信息， 0：不需付费，1：付费
     */
    public final static String SERVICE_REQUEST_CHARGE_STATUS = "service-request-charge-status";

    /**
     * 租户访问的ip地址
     */
    public final static String TENANT_ACCESS_IP = "tenant-access-ip";

    /**
     * 请求唯一id，补课超过64位
     */
    public final static String REQUEST_UNIQUE_ID ="request-unique-id";

    /**
     * 腾讯反欺诈uri路径
     */
    public final static String TENCENT_ANTI_FRAUD_V1 = "/micro_crawler/tencent/antiFraud";

    /**
     * 请求错误码
     */
    public final static String SERVICE_REQUEST_ERROR_CODE = "service-request-error-code";

    public final static String IP = "ip";

    public final static String AUTH = "auth";

    public final static String ROUTE = "route";

    public final static String CHECK_GATEWAY_BAFFLE = "false";
}
