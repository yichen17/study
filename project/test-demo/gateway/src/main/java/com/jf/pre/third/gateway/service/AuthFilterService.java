package com.jf.pre.third.gateway.service;

import com.jf.pre.third.gateway.bean.RedisUtil;
import com.jf.pre.third.gateway.dao.TenantAuthDao;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;


/**
 * 权限校验service类
 * @author liumg
 * @version 创建时间：2019/6/27.
 */
@RefreshScope
@Service
public class AuthFilterService {

    private static Logger logger= LoggerFactory.getLogger(AuthFilterService.class );

    /**
     * V1版本请求连接正则表达式
     */
    private static final String URL_PATTERN_V1= "/api-gateway/(\\w+)/v1/\\S+$";

    /**
     *请求连接正则表达式
     */
    private static final String URL_PATTERN= "/api-gateway/(\\w+)/v2/\\S+$";

    /**
     * 鉴权正则表达式
     */
    private static final String OLD_AUTH_PATTERN = "appid:(\\w+),security:(\\w+),encrypt:clear";

    /**
     * 鉴权错写
     */
    private static final String OLD_AUTH_PATTERN_1 = "appid:(\\w+),security:(\\w+),encrypty:clear";

    /**
     * 请求唯一id
     */
    private static final String UNIQUE_ID_PREFIX = "UNIQUE_ID_";

    @Autowired
    TenantAuthDao tenantAuthDao;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RedisUtil redisUtil;
    /**
     * 网关根据连接特征进行，serviceId重定向
     * @param exchange serverWebExchange实体类
     * @return 返回新的路由信息
     */
    public Route redefineRoute(ServerWebExchange exchange) {

        String rawPath = exchange.getRequest().getURI().getRawPath();
        String serivceId = rawPath.split("/")[2];
        String version = rawPath.split("/")[3];

        URI newUri = null;
        try {
            newUri = new URI("lb://" + serivceId.toUpperCase() + "-" + version.toUpperCase());
        }catch (Exception e) {
            e.printStackTrace();
        }

        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        Route newRoute = Route.async().asyncPredicate(route.getPredicate())
                .filters(route.getFilters())
                .uri(newUri)

                .id(route.getId())
                .order(2000)
                .build();
        return newRoute;
    }
    /**
     * V1版本检验是否为正常的服务请求地址， 正则表达式
     * @param uri 请求服务的uri
     * @return 合法返回true，非法返回false
     */
    public boolean checkValidRequestUrlV1(String uri) {
        return Pattern.matches(URL_PATTERN_V1, uri);
    }


    /**
     * 检验是否为正常的服务请求地址， 正则表达式
     * @param uri 请求服务的uri
     * @return 合法返回true，非法返回false
     */
    public boolean checkValidRequestUrl(String uri) {
        return Pattern.matches(URL_PATTERN, uri);
    }

    private String getRegexResult(String regex, String content, int index) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        if(matcher.find()){
            return matcher.group(index);
        }
        return null;
    }

    public String getProductByUriV1(String uri) {
        return getRegexResult(URL_PATTERN_V1, uri, 1);
    }

    public String getProductByUri(String uri) {
        return getRegexResult(URL_PATTERN, uri, 1);
    }

    public String getAppIdByAuthHeader(String header) {
        String appId = getRegexResult(OLD_AUTH_PATTERN, header, 1);
        if (appId == null) {
            return  getRegexResult(OLD_AUTH_PATTERN_1, header, 1);
        }
        return appId;
    }

    public String getTokenByAuthHeader(String header) {
        String token = getRegexResult(OLD_AUTH_PATTERN, header, 2);
        if (StringUtils.isEmpty(token)) {
            return getRegexResult(OLD_AUTH_PATTERN_1, header, 2);
        }
        return token;
    }

    /**
     * 检验是否为合法的商户id
     * @param appId 商户唯一号
     * @param token 商户token
     * @return 合法返回true，失败返回false
     */
    public boolean checkValidByAppIdAndToken(String appId, String token, String product) {

        String key = new StringBuilder("auth")
                .append("-").append(appId)
                .append("-").append(token)
                .append("-").append(product).toString();

        try {
            boolean isChecked = redisUtil.exists(key);
            if (isChecked) {
                return true;
            }
        } catch (Exception e) {
            logger.error("系统redis缓存操作发生异常, 查询数据进行操作，key={}, 异常信息为：", key, e);
            int count = tenantAuthDao.hasValidTenantByAppIdAndToken(appId, token, product);
            if (count == 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为合法的授权头
     * @param header Auth头信息内容
     * @return 合法返回true，不合法返回false
     */
    public boolean isVaidAuthFormat(String header) {

        if (!StringUtils.isEmpty(header)) {
            return Pattern.matches(OLD_AUTH_PATTERN, header.trim());
        }
        return false;
    }

    /**
     * 判断是否为合法的授权头
     * @param header Auth头信息内容
     * @return 合法返回true，不合法返回false
     */
    public boolean isVaidAuthFormat2(String header) {

        if (!StringUtils.isEmpty(header)) {
            return Pattern.matches(OLD_AUTH_PATTERN_1, header.trim());
        }
        return false;
    }

    /**
     * 校验网络白名单是否合法
     * @param appId 商户唯一Id
     * @return 合法返回true，非法返回false
     */
    public boolean checkNetworkPermission(String appId, String product, String ip) {

        String key = new StringBuilder("white-ip")
                .append("-").append(appId)
                .append("-").append(product).toString();

        try {
            Object value = redisUtil.get(key);
            if (value != null) {
                String ips = value.toString();
                return ips.contains(ip);
            }
        } catch (Exception e) {
            logger.error("系统redis发生异常，直接查询数据进行鉴权，key={}, 异常信息：", key, e);
            int count = tenantAuthDao.selectNetworkPermissionRegexByAppId(appId, product, ip);
            if (count == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRepeatRequest(String uniqueId, String requestId) {

        if (StringUtils.isEmpty(uniqueId)) {
            return false;
        }

        String key = UNIQUE_ID_PREFIX + uniqueId;
        try {
            long start = System.currentTimeMillis();
            int expireTime = RandomUtils.nextInt(5, 30);
            int isLock = redisUtil.tryLock(key, requestId, expireTime);
            if (isLock == 0) {
                logger.info("获取唯一锁成功 " + isLock + " ,cost:" + (System.currentTimeMillis() - start));
                return false;
            }
            logger.info("is exist　" + isLock + " ,cost:" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            logger.error("调用redis发生异常，e=", e);
        }
        return true;
    }

    public boolean releaseLock(String uniqueId) {
        String key = UNIQUE_ID_PREFIX + uniqueId;
        return redisUtil.releaseLock(key, null);
    }

    public String getIpAddress(ServerWebExchange exchange) {

        String unknown = "unknown";
        String comma = ",";

        ServerHttpRequest request = exchange.getRequest();
        for (String key : request.getHeaders().keySet()) {
            for (String header : request.getHeaders().get(key)) {
                logger.info("请求头信息 key:{}    value:{}", key, header);
            }
        }

        String ip = request.getHeaders().getFirst("x-forwarded-for");
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("x-real-ip");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
        }

        logger.info("当前获取服务端ip为：{}", ip);
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = exchange.getRequest().getRemoteAddress().getHostString();
        }

        logger.info("当前获取服务端最终ip为：{}", ip);


        if (ip.contains(comma)) {
            return ip.split(comma)[0];
        }


        return ip;
    }
}
