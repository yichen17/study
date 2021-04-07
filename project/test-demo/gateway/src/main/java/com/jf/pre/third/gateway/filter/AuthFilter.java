package com.jf.pre.third.gateway.filter;

import com.jf.pre.third.gateway.exception.GatewayException;
import com.jf.pre.third.gateway.service.AuthFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.jf.pre.third.gateway.constants.GatewayConstants.*;
import static com.jf.pre.third.gateway.exception.GatewayExceptionCode.*;

/**
 *  网络白名单校验，商户id校验
 * @author liumg
 * @version 创建时间：2019/6/25.
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static Logger logger= LoggerFactory.getLogger( AuthFilter.class );

    @Autowired
    AuthFilterService authFilterService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().getRawPath();
        String authorization = request.getHeaders().getFirst("Authorization");
        String ip = authFilterService.getIpAddress(exchange);
        String requestId = exchange.getRequest().getHeaders().getFirst(SERVICE_REQUEST_ID);
        logger.info("调用网关入口请求:requestId=【{}】,uri=【{}】",requestId,uri);
        String uniqueId = request.getHeaders().getFirst(REQUEST_UNIQUE_ID);
        if (authFilterService.checkRepeatRequest(uniqueId, requestId)) {
            logger.info("服务端反复同一任务被禁用，auth={}, uniqueId={}, requestId={}", authorization, uniqueId, requestId);
            throw new GatewayException(ACCESS_REPEAT_DENY);
        }

        //检查是否为合法的请求路径
        if (!authFilterService.checkValidRequestUrl(uri)) {
            logger.info("非鉴权类V2连接地址，直接转发到后端，transNo:{}, uri:{}", requestId, uri);
            return chain.filter(exchange);
        }

        ServerHttpRequest newRequest = exchange.getRequest().mutate().header(SERVICE_REQUEST_ID, requestId).build();
        ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

        logger.info("网关权限鉴权，transNo:{}, uri:{}, ip:{}, body:{}", requestId, uri, ip, authorization);
        newExchange.getAttributes().put(LOGGING_START_TIME, System.currentTimeMillis());
        newExchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        exchange.getAttributes().put(SERVICE_REQUEST_ID, requestId);

        //判断是否为合法格式的鉴权信息
        if (!authFilterService.isVaidAuthFormat(authorization)) {
            logger.info("鉴权失败，格式不正确， authorization={}, requestId={}", authorization, requestId);
            throw new GatewayException(AUTH_ACCESS_INVALID);
        }

        String product = authFilterService.getProductByUri(uri);
        String appId = authFilterService.getAppIdByAuthHeader(authorization);
        String token = authFilterService.getTokenByAuthHeader(authorization);
        exchange.getAttributes().put(SERVICE_REQUEST_NAME, product);
        exchange.getAttributes().put(TENANT_ID, appId);
        exchange.getAttributes().put(TENANT_ACCESS_IP, ip);

        //验证网络白名单
        if (!authFilterService.checkNetworkPermission(appId, product, ip)) {
            logger.info("网络权限受限失败，product={}, appId={}, ip={}, requestId={}", product, appId, ip, requestId);
            throw new GatewayException(AUTH_ACCESS_IP_DENY);
        }

        //验证appId和token
        if (!authFilterService.checkValidByAppIdAndToken(appId, token, product)) {
            logger.info("网络权限受限失败，product={}, appId={}, ip={}, requestId={}", product, appId, ip, requestId);
            throw new GatewayException(AUTH_ACCESS_DENY);
        }

        ServerHttpRequest forwardRequest = exchange.getRequest().mutate()
                .header(TENANT_ID, appId)
                .header(SERVICE_REQUEST_NAME, product)
                .build();

        newExchange = exchange.mutate().request(forwardRequest).build();
        logger.info("网关权限鉴权成功，转发路由请求， uri={}, transNo={}", uri, requestId);
        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return 3000;
    }

}
