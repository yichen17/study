package com.jf.crawl.cloud.callback.bean;

import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 异步httpclient实现bean
 * @author liumg
 * @version 创建时间：2018/12/29.
 */
@Configuration
@Component
public class AsyncRequestUtil {

    private static Logger logger = LoggerFactory.getLogger(AsyncRequestUtil.class);

    @Value("${async.request.socket.timeout}")
    private int socketTimeout = 5* 1000;

    @Value("${async.request.connect.timeout}")
    private int connectTimeout = 5 * 1000;

    @Value("${async.request.max.conn.total}")
    private int maxConnTotal = 1000;

    @Value("${async.request.max.per.route}")
    private int maxConnPerRoute = 2000;

    /**
     * 初始化httpsclient
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    @PostConstruct
    public CloseableHttpClient initHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        Unirest.setObjectMapper(new ObjectMapper() {

            private org.codehaus.jackson.map.ObjectMapper jacksonObjectMapper  = new org.codehaus.jackson.map.ObjectMapper();

            @Override
            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return initHttpClient(false);
    }

    public CloseableHttpClient initHttpClient(boolean secure) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        RequestConfig defaultRequestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                //此处使用默认的Cookie规范，可以让后续请求共享Cookie
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setRedirectsEnabled(true)
                .setCircularRedirectsAllowed(false)
                .build();

        HttpClientBuilder builder = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .setMaxConnTotal(maxConnTotal)
                .setMaxConnPerRoute(maxConnPerRoute)
                .setConnectionReuseStrategy(NoConnectionReuseStrategy.INSTANCE);

        if (secure) {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            builder.setSSLSocketFactory(new SSLConnectionSocketFactory(sslContext));
        }

        return builder.build();
    }

    /**
     * GET 方法响应转为字符串
     * @param url 地址
     * @return 网页文本
     * @throws Exception
     */
    public String get(String url) throws Exception {
        return get(url, null);
    }

    public String get(String url, final Map<String, Object> params) throws Exception {
        return get(url, params, null);
    }

    public String get(String url, final Map<String, Object> params, Map<String, String> headers) throws Exception {
        printRequest(url, params, headers);
        LinkedHashMap<String, Object> queryStringMap = params != null ? new LinkedHashMap<String, Object>() {{
            putAll(params);
        }} : null;

        HttpResponse<String> response = Unirest.get(url)
            .headers(headers)
            .queryString(queryStringMap)
            .asString();
        return printResponse(response.getBody());
    }

    public HttpResponse<String> post(String url, final Map<String, Object> params) throws Exception {
        return post(url, params, null);
    }

    public HttpResponse<String> post(String url, final Map<String, Object> params, Map<String, String> headers) throws UnirestException {

        printRequest(url, params, headers);
        Map<String, Object> fields = params != null ? new LinkedHashMap<String, Object>() {{
            putAll(params);
        }} : null;

        HttpResponse<String> response = Unirest.post(url)
                .headers(headers)
                .fields(fields)
                .asString();
        return response;
    }

    
    public HttpResponse<String> postJson(String url, final Map<String, Object> params, Map<String, String> headers) throws Exception {

        printRequest(url, params, headers);
        Map<String, Object> fields = params != null ? new LinkedHashMap<String, Object>() {{
            putAll(params);
        }} : null;

        HttpResponse<String> response = Unirest.post(url)
            .headers(headers)
            .body(fields)
            .asString();
        return response;
    }

    public HttpResponse<String> postAsJson(String url, final Map<String, Object> params, Map<String, String> headers) throws UnirestException {

        printRequest(url, params, headers);
        Map<String, Object> fields = params != null ? new LinkedHashMap<String, Object>() {{
            putAll(params);
        }} : null;

        HttpResponse<String> response = Unirest.post(url)
            .headers(headers)
            .header("content-type", "application/json;charset=UTF-8")
            .body(JSON.toJSONString(fields))
            .asString();
        return response;
    }
    
    String printResponse(String response) {
        logger.info("               Response 详情：");
        logger.info("=================================Response=====================================");
        logger.info("Response => {}", response);
        logger.info("=================================Response=====================================");
        return response;
    }

    private static void printRequest(String url, Map<String, Object> requestParameters, Map<String, String> headers) {
        logger.info("URL => {}", url);
        logger.info("=================================Request Start====================================");
        if(headers != null) {
            for (Map.Entry<String, String> entry: headers.entrySet()) {
                logger.info("{} : {}", entry.getKey(), entry.getValue());
            }
        }
        logger.info("=================================Request Parameters ==============================");
        if (requestParameters != null) {
            for (Map.Entry<String, Object> entry: requestParameters.entrySet()) {
                logger.info("{} : {}", entry.getKey(), entry.getValue());
            }
        }
        logger.info("=================================Request End =====================================");
    }
    
    public static void main(String[] args) throws Exception {
    	
    	Map<String, Object> maps = new HashMap<>(12);
    	maps.put("task_key", "18070310CRED8vlltb90");
    	maps.put("err_code", "asdfafs");
    	maps.put("err_code_msg", "是");
    	maps.put("status_code", "391");
    	
//    	{
//    		"task_key":"18070310CRED8vlltb90",
//    		"err_code":"asdfafs",
//    		"err_code_msg":"是是是",
//    		"status_code":"391"
//    	}

        AsyncRequestUtil asyncRequestUtil = new AsyncRequestUtil();
        asyncRequestUtil.postAsJson("http://101.201.101.195:8555/schedule-api/task/18070310CRED8vlltb90/status/u", maps, null);
    	
    }
}
