package com.jf.pre.third.gateway.filter.v1;

import com.alibaba.fastjson.JSONObject;
import com.jf.pre.third.gateway.constants.GatewayConstants;
import com.jf.pre.third.gateway.exception.v1.GatewayV1Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.jf.pre.third.gateway.constants.GatewayConstants.CHECK_GATEWAY_BAFFLE;
import static com.jf.pre.third.gateway.exception.v1.GatewayV1ExceptionCode.AUTH_ACCESS_DENY;

@Component
public class BaffleFilter implements GlobalFilter, Ordered {

    private static Logger logger = LoggerFactory.getLogger(BaffleFilter.class);

    @Value("${gateway_baffle_url:false}")
    private String gatewayBaffleUrl;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String uri = request.getURI().getRawPath();
        String requestId = UUID.randomUUID().toString();

        //添加挡板验证功能
        if (!gatewayBaffleUrl.equals(CHECK_GATEWAY_BAFFLE)) {
            logger.info("requestId:{}，当前环境已启动挡板",requestId);
            Mono mono = postBaffle(exchange, requestId, uri);
            if(!StringUtils.isEmpty(mono)) {
                return mono;
            }
        }
        return chain.filter(exchange);
    }

    private Mono<Void> postBaffle(ServerWebExchange exchange, String requestId, String uri) {
        // 请求体
        JSONObject params = new JSONObject();
        params.put("request_id", requestId);
        params.put("uri", uri);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(gatewayBaffleUrl, new HttpEntity<>(params, headers), JSONObject.class);
        JSONObject body = responseEntity.getBody();
        boolean status = body.getBoolean("status");
        boolean flag = body.getBoolean("flag");
        if (!status) {
            if (flag) {
                JSONObject jsonObject = body.getJSONObject("data");
                exchange.getResponse().setStatusCode(HttpStatus.OK);
                byte[] bytes = (jsonObject.toJSONString()).getBytes(StandardCharsets.UTF_8);
                DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(bytes);
                return exchange.getResponse().writeWith(Flux.just(wrap));
            } else {
                throw new GatewayV1Exception(AUTH_ACCESS_DENY);
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
