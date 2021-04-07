package com.jf.pre.third.gateway.filter.v1;

import com.jf.pre.third.gateway.exception.v1.GatewayV1Exception;
import com.jf.pre.third.gateway.service.AuthFilterService;
import com.jf.pre.third.gateway.constants.GatewayConstants;
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

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jf.pre.third.gateway.exception.v1.GatewayV1ExceptionCode.AUTH_ACCESS_DENY;
import static com.jf.pre.third.gateway.exception.v1.GatewayV1ExceptionCode.AUTH_ACCESS_IP_DENY;

/**
 *  V1版本权限管理，网络白名单校验，商户id校验
 * @author liumg
 * @version 创建时间：2019/6/25.
 */
@Component
public class AuthFilterV1 implements GlobalFilter, Ordered {

    private static Logger logger= LoggerFactory.getLogger( AuthFilterV1.class );

    @Autowired
    AuthFilterService authFilterService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().getRawPath();
        exchange.getAttributes().put(GatewayConstants.LOGGING_START_TIME, System.currentTimeMillis());
        String requestId = UUID.randomUUID().toString();

        ServerHttpRequest newRequest = exchange.getRequest().mutate().header(GatewayConstants.SERVICE_REQUEST_ID, requestId).build();
        exchange = exchange.mutate().request(newRequest).build();
        //检查是否为V1版本合法的请求路径
        if (!authFilterService.checkValidRequestUrlV1(uri) && !uri.contains(GatewayConstants.TENCENT_ANTI_FRAUD_V1)) {
            logger.info("非V1鉴权请求地址，直接转发到V2 AuthFilter uri = {}, uri:{}", uri);
            return chain.filter(exchange);
        }

        String authorization = request.getHeaders().getFirst("Authorization");
        String ip = authFilterService.getIpAddress(exchange);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        exchange.getAttributes().put(GatewayConstants.SERVICE_REQUEST_ID, requestId);
        logger.info("V1网关权限鉴权，requestId:{}, uri:{}, ip:{}, body:{}", requestId, uri, ip, authorization);

        //判断是否为合法格式的鉴权信息
        if ((!authFilterService.isVaidAuthFormat(authorization)) && (!authFilterService.isVaidAuthFormat2(authorization))) {
            logger.info("V1鉴权失败，格式不正确， authorization={}, requestId={}", authorization, requestId);
            throw new GatewayV1Exception(AUTH_ACCESS_DENY);
        }

        String product = authFilterService.getProductByUriV1(uri);
        if (uri.contains(GatewayConstants.TENCENT_ANTI_FRAUD_V1)) {
            product = "TXAF";
        }

        String appId = authFilterService.getAppIdByAuthHeader(authorization);
        String token = authFilterService.getTokenByAuthHeader(authorization);
        exchange.getAttributes().put(GatewayConstants.SERVICE_REQUEST_NAME, product);
        exchange.getAttributes().put(GatewayConstants.TENANT_ID, appId);
        exchange.getAttributes().put(GatewayConstants.TENANT_ACCESS_IP, ip);

        //验证网络白名单
        if (!authFilterService.checkNetworkPermission(appId, product, ip)) {
            logger.info("网络权限受限失败，product={}, appId={}, ip={}, requestId={}", product, appId, ip, requestId);
            throw new GatewayV1Exception(AUTH_ACCESS_IP_DENY);
        }

        //验证appId和token
        if (!authFilterService.checkValidByAppIdAndToken(appId, token, product)) {
            logger.info("网络权限受限失败，product={}, appId={}, ip={}, requestId={}", product, appId, ip, requestId);
            throw new GatewayV1Exception(AUTH_ACCESS_DENY);
        }

        ServerHttpRequest forwardRequest = exchange.getRequest().mutate()
                .header(GatewayConstants.TENANT_ID, appId)
                .header(GatewayConstants.SERVICE_REQUEST_NAME, product)
                .build();

        ServerWebExchange newExchange = exchange.mutate().request(forwardRequest).build();
        logger.info("网关权限鉴权成功，转发路由请求， uri={}, transNo={}", uri, requestId);
        return chain.filter(newExchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }

    public static void main(String[] args) {

        String regex = "/api-gateway/(\\w+)/v2/\\S+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("/api-gateway/mobile/v2/test");
        if(matcher.find()){
            System.out.println(matcher.group(1));
        }
    }
}
