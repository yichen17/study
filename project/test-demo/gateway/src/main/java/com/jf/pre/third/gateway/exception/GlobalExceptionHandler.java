package com.jf.pre.third.gateway.exception;

import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.fastjson.JSONObject;
import com.jf.pre.third.gateway.exception.v1.GatewayV1Exception;
import com.jf.pre.third.gateway.exception.v1.GatewayV1ExceptionCode;
import com.jf.pre.third.gateway.pojo.GlobalErrorVo;
import com.jf.pre.third.gateway.pojo.v1.GlobalErrorVoV1;
import com.jf.pre.third.gateway.service.AuthFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.*;

import static com.jf.pre.third.gateway.constants.GatewayConstants.*;
import static com.jf.pre.third.gateway.exception.GatewayExceptionCode.SERVICE_ACCESS_LIMIT_EXCEED;

/**
 * @classDesc: 统一异常处理,参考{@link org.springframework.web.server.AbstractErrorWebExceptionHandler}修改
 * @author: Eric liu
 * @createTime: 2018/10/30
 */
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    AuthFilterService authFilterService;

    /**
     * MessageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    /**
     * MessageWriter
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

    /**
     * ViewResolvers
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    /**
     * 存储处理异常后的信息
     */
    private ThreadLocal<Map<String,Object>> exceptionHandlerResult = new ThreadLocal<>();

    /**
     * 参考AbstractErrorWebExceptionHandler
     * @param messageReaders
     */
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     * @param viewResolvers
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     * @param messageWriters
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }

    /**
     * 按照异常类型进行处理
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        String path = exchange.getRequest().getURI().getRawPath();
        Map<String,Object> result = new HashMap<>(2);

        String uniqueId = exchange.getRequest().getHeaders().getFirst(REQUEST_UNIQUE_ID);
        if (!StringUtils.isEmpty(uniqueId)) {
            //先释放锁
            if (authFilterService.releaseLock(uniqueId)) {
                logger.info("释放唯一id锁成功");
            }
        }

        //如果是V2版本的异常信息
        if (authFilterService.checkValidRequestUrl(path)) {
            handleGlobalExceptionV2(exchange, ex, result);

        } else {
            handleGlobalExceptionV1(exchange, ex, result);
        }

        ServerHttpRequest request = exchange.getRequest();
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        exceptionHandlerResult.set(result);
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> write(exchange, response));
    }

    /**
     * 参考DefaultErrorWebExceptionHandler
     * @param request
     * @return
     */
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String,Object> result = exceptionHandlerResult.get();
        //删除对应存储的错误信息，以防止OOM
        exceptionHandlerResult.remove();

        return ServerResponse.status((HttpStatus) result.get("httpStatus"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(result.get("body")));
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     * @param exchange
     * @param response
     * @return
     */
    private Mono<? extends Void> write(ServerWebExchange exchange, ServerResponse response) {
        exchange.getResponse().getHeaders().setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return GlobalExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return GlobalExceptionHandler.this.viewResolvers;
        }
    }

    private void handleGlobalExceptionV2(ServerWebExchange exchange, Throwable ex, Map<String,Object> result) {


        String path = exchange.getRequest().getURI().getRawPath();
        String requestId = exchange.getAttribute(SERVICE_REQUEST_ID);
        requestId = (requestId == null) ? UUID.randomUUID().toString() : requestId;

        GlobalErrorVo globalErrorVo = new GlobalErrorVo();
        globalErrorVo.setPath(path);
        globalErrorVo.setTimestamp(System.currentTimeMillis());
        globalErrorVo.setRequestId(requestId);
        globalErrorVo.setMessage("Internal Server Error");
        GatewayExceptionCode gatewayExceptionCode = GatewayExceptionCode.SERVICE_INTERNAL_ERROR;
        HttpStatus httpStatus;

        if (ex instanceof GatewayException) {
            GatewayException gatewayException = (GatewayException) ex;
            httpStatus = HttpStatus.OK;
            globalErrorVo.setMessage("Access Deny");
            globalErrorVo.setCode(httpStatus.value());
            gatewayExceptionCode = gatewayException.getCode();
        }else if ((ex instanceof FlowException) || (ex instanceof ParamFlowException)) {
            httpStatus = HttpStatus.FORBIDDEN;
            globalErrorVo.setMessage("Access Rate Exceed Limit");
            gatewayExceptionCode = SERVICE_ACCESS_LIMIT_EXCEED;
        }else if (ex instanceof NotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            globalErrorVo.setMessage("Service Not Found");
            gatewayExceptionCode = GatewayExceptionCode.SERVICE_NOT_FOUND;
        }else {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            httpStatus = responseStatusException.getStatus();
            if (httpStatus == HttpStatus.NOT_FOUND) {
                globalErrorVo.setMessage("Service Not Found");
                gatewayExceptionCode = GatewayExceptionCode.SERVICE_NOT_FOUND;
            }
            logger.error("[全局异常处理], RequestId:{}, 异常请求路径:{},记录异常信息:",requestId, path, new Exception(ex));
        }

        globalErrorVo.setCode(httpStatus.value());
        globalErrorVo.setError(gatewayExceptionCode.getValue());
        globalErrorVo.setErrors(gatewayExceptionCode.getDesc());
        result.put("httpStatus",httpStatus);
        result.put("body", globalErrorVo);
    }

    private void handleGlobalExceptionV1(ServerWebExchange exchange, Throwable ex, Map<String,Object> result) {

        String path = exchange.getRequest().getURI().getRawPath();
        String requestId = exchange.getAttribute(SERVICE_REQUEST_ID);
        requestId = (requestId == null) ? UUID.randomUUID().toString() : requestId;

        GlobalErrorVoV1 globalErrorVoV1 = new GlobalErrorVoV1();
        Long startTime = exchange.getAttribute(LOGGING_START_TIME);
        if (startTime == null) {
            startTime = System.currentTimeMillis();
        }
        globalErrorVoV1.setSpendTime(String.valueOf(System.currentTimeMillis() - startTime));
        globalErrorVoV1.setStartTime(startTime);
        globalErrorVoV1.setTransNo(requestId);
        globalErrorVoV1.setStatus("-1");

        if (ex instanceof GatewayV1Exception) {
            GatewayV1Exception gatewayException = (GatewayV1Exception) ex;
            globalErrorVoV1.setErrorCode(gatewayException.getCode());
        }  else if (ex instanceof FlowException || ex instanceof ParamFlowException) {
            globalErrorVoV1.setErrorCode(SERVICE_ACCESS_LIMIT_EXCEED.getValue());
            result.put("httpStatus", HttpStatus.OK);
            result.put("body", JSONObject.toJSONString(globalErrorVoV1));
            logger.error("[全局异常处理], RequestId:{}, 异常请求路径:{},记录异常信息:",requestId, path, new Exception(ex));
            return;
        } else {
            globalErrorVoV1.setErrorCode(GatewayV1ExceptionCode.SERVICE_INTERNAL_ERROR.getValue());
        }

        result.put("httpStatus", HttpStatus.OK);
        result.put("body", JSONObject.toJSONString(globalErrorVoV1));
        logger.error("[全局异常处理], RequestId:{}, 异常请求路径:{},记录异常信息:",requestId, path, new Exception(ex));
    }

}