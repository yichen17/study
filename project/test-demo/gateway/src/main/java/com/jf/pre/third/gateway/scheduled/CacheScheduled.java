package com.jf.pre.third.gateway.scheduled;

import com.jf.pre.third.gateway.bean.RedisUtil;
import com.jf.pre.third.gateway.dao.GatewayRouteDao;
import com.jf.pre.third.gateway.dao.TenantAuthDao;
import com.jf.pre.third.gateway.entity.GatewayAuthEntity;
import com.jf.pre.third.gateway.entity.GatewayRouteEntity;
import com.jf.pre.third.gateway.entity.GatewayWhiteIpEntity;
import com.jf.pre.third.gateway.route.DynamicRouteServiceImpl;
import com.jf.pre.third.gateway.route.LocalRouteDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author liumg
 * @version 创建时间：2019/6/28.
 */
@EnableScheduling
@Component
public class CacheScheduled {

    private static Logger logger= LoggerFactory.getLogger( CacheScheduled.class );

    @Autowired
    GatewayRouteDao gatewayRouteDao;

    @Autowired
    DynamicRouteServiceImpl dynamicRouteService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    TenantAuthDao tenantAuthDao;

    @Autowired
    LocalRouteDefinitionRepository localRouteDefinitionRepository;

    private Set<String> allGatewayAuthKeys = new HashSet<>(32);

    private Set<String> allGatewayWhiteIps = new HashSet<>(32);

    @PostConstruct
    public void reloadGatewayRoute() {

        //路由信息，只能新增、删除路由，不能更新路由
        List<GatewayRouteEntity> allGatewayRoute = gatewayRouteDao.selectAllGatewayRoute();
        Map<String, Integer> ids = new HashMap<>(32);
        for (GatewayRouteEntity gatewayRoute : allGatewayRoute) {
            String id = gatewayRoute.getId();
            ids.put(id, 1);
            if (localRouteDefinitionRepository.exists(id)) {
                continue;
            }

            //新增路由
             logger.info("新增路由信息，id={}, 路由信息={}, ", id, gatewayRoute.toString());
             dynamicRouteService.update(gatewayRoute.getRouteDefinition());
        }

        //删除数据库已清除路由
        for (String id : localRouteDefinitionRepository.allRouteIds()) {
            if (!ids.containsKey(id)) {
                dynamicRouteService.delete(id);
                logger.info("删除网关路由信息，id={}", id);
            }
        }
    }

    @PostConstruct
    public void reloadGatewayAuth() {

        Set<String> keys = new HashSet<>();
        List<GatewayAuthEntity> allGatewayAuth =  tenantAuthDao.selectAllValidTenant();
        for (GatewayAuthEntity gatewayAuthEntity : allGatewayAuth) {

            String appId = gatewayAuthEntity.getAppId();
            String token = gatewayAuthEntity.getToken();
            String product = gatewayAuthEntity.getProduct();
            String key = new StringBuilder("auth-")
                    .append(appId)
                    .append("-").append(token)
                    .append("-").append(product).toString();

            keys.add(key);
            allGatewayAuthKeys.add(key);
            redisUtil.set(key, System.currentTimeMillis());
            logger.info("刷新鉴权信息 key={}", key);

        }

        for (String key : allGatewayAuthKeys) {
            if (!keys.contains(key)) {
                dynamicRouteService.delete(key);
                allGatewayAuthKeys.remove(key);
                redisUtil.remove(key);
                logger.info("删除新增鉴权信息，key={}", key);
            }
        }
    }

    @PostConstruct
    public void reloadGatewayWhiteIps() {

        Map<String, String> whiteIpMap = new HashMap<>(8);
        Set<String> keys = new HashSet<>();
        List<GatewayWhiteIpEntity> allTenantWhiteIps = tenantAuthDao.selectAllTenantWhiteIps();
        for (GatewayWhiteIpEntity gatewayWhiteIpEntity : allTenantWhiteIps) {
            String appId = gatewayWhiteIpEntity.getAppId();
            String ip = gatewayWhiteIpEntity.getIp();
            String product = gatewayWhiteIpEntity.getProduct();
            String key = "white-ip-" + appId + "-" + product;

            if (whiteIpMap.containsKey(key)) {
                String value = whiteIpMap.get(key) + "," + ip;
                whiteIpMap.put(key, value);
            } else {
                whiteIpMap.put(key, ip);
            }
        }

        for (String key : whiteIpMap.keySet()) {
            String ips = whiteIpMap.get(key);
            keys.add(key);
            allGatewayWhiteIps.add(key);
            redisUtil.set(key, ips);
            logger.info("更变鉴权白名单 key={}， ips={}", key, ips);
        }

        for (String key : allGatewayWhiteIps) {
            if (!keys.contains(key)) {
                allGatewayWhiteIps.remove(key);
                redisUtil.remove(key);
                logger.info("删除鉴权白名单，key={}", key);
            }
        }
    }
}
