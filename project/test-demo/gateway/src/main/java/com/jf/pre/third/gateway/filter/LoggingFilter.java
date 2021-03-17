package com.jf.pre.third.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.jf.pre.third.gateway.service.AuthFilterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.jf.pre.third.gateway.constants.GatewayConstants.*;

/**
 * 记录调用详情，包括路由信息，花费时长等
 * @author liumg
 * @version 创建时间：2019/7/15.
 */
@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static Logger logger= LoggerFactory.getLogger( LoggingFilter.class );

    private static Logger authFilterLogger =  LoggerFactory.getLogger( AuthFilter.class );

    @Autowired
    AuthFilterService authFilterService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        return chain.filter(exchange).then( Mono.fromRunnable(() -> {
            HttpHeaders headers = exchange.getResponse().getHeaders();
            Long startTime = exchange.getAttribute(LOGGING_START_TIME);
            String appId = exchange.getAttribute(TENANT_ID);
            String serviceName =exchange.getAttribute(SERVICE_REQUEST_NAME);
            String ip = exchange.getAttribute(TENANT_ACCESS_IP);
            String extDataHeader = exchange.getRequest().getHeaders().getFirst(SERVICE_REQUEST_EXT_DATA);

            String route = headers.getFirst(SERVICE_ROUTE);
            String transNo = headers.getFirst(SERVICE_REQUEST_ID);
            String status = headers.getFirst(SERVICE_REQUEST_ROUTE_STATUS);
            String errorCode = headers.getFirst(SERVICE_REQUEST_ERROR_CODE);
            String extData = "";
            String uniqueId = exchange.getRequest().getHeaders().getFirst(REQUEST_UNIQUE_ID);
            String charge = headers.getFirst(SERVICE_REQUEST_CHARGE_STATUS);

            if (StringUtils.isEmpty(extDataHeader)) {
                extDataHeader = headers.getFirst(SERVICE_REQUEST_EXT_DATA);
            }

            //优先去response的流水号
            if (StringUtils.isEmpty(transNo)) {
                transNo = exchange.getAttribute(SERVICE_REQUEST_ID);
            }

            if (!StringUtils.isEmpty(extDataHeader)) {
                extData = parseExtData(extDataHeader);
            } else {
                extData = "null,null,null,null";
            }

            String uri = exchange.getRequest().getURI().getRawPath();
            String costTime = String.valueOf(System.currentTimeMillis() - startTime);
            int code = exchange.getResponse().getStatusCode().value();

            logger.info("{},{},{},{},{},{},{},{},{},{},{},{}",
                    uri, serviceName, appId, ip, transNo, route, costTime, code, errorCode, status, extData, charge);

            //释放并发锁
            if (!StringUtils.isEmpty(uniqueId)) {
                boolean isRelease = authFilterService.releaseLock(uniqueId);
                authFilterLogger.info("删除并发锁， uniqueId={}, isRelease={}", uniqueId, isRelease);
            }
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    /**
     * 解析请求头存在的扩展参数
     * @param header 扩展头信息，回调大数据专用
     * @return 返回sourceId
     */
    private String parseExtData(String header) {
        try {
            header = header.replace("-", "+").replace("_", "/");
            String content = new String(Base64Utils.decodeFromString(header));
            JSONObject extDataJson = JSONObject.parseObject(content);
            String sourceUserId = extDataJson.getString("sourceUserId");
            String sysSourceId = extDataJson.getString("sysSourceId");
            String certId = extDataJson.getString("certId");
            String realName = extDataJson.getString("realName");
            return sourceUserId + "," + sysSourceId + "," + certId + "," + realName;
        } catch (Exception e) {
            authFilterLogger.error("解析扩展字段失败，header={}", header);
        }

        return "null,null,null,null";
    }
}
