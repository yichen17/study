package com.jf.pre.third.gateway.listener;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.jf.pre.third.gateway.scheduled.CacheScheduled;
import com.jf.pre.third.gateway.constants.GatewayConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.alibaba.nacos.NacosConfigProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

/**
 * @author liumg
 * @version 创建时间：2019/8/29.
 */

@Component
public class RefreshConfigListener {

    private static Logger logger= LoggerFactory.getLogger(RefreshConfigListener.class );

    @Autowired
    NacosConfigProperties nacosConfigProperties;

    private final String dataId = "refresh";

    private final String group = "com.jf.crawl.gateway";

    @Autowired
    CacheScheduled cacheScheduled;

    ConfigService configService;

    @PostConstruct
    public void start() {
        configService = nacosConfigProperties.configServiceInstance();
        try {
            configService.addListener(dataId, group, new ConfigListener());
        } catch (Exception e) {
            logger.error("添加网关刷新监听器失败 err=", e);
        }
    }

    private class ConfigListener implements Listener{

        /**
         * Executor to excute this receive
         *
         * @return Executor
         */
        @Override
        public Executor getExecutor() {
            return null;
        }

        /**
         * 接收配置信息
         * @param content 配置值
         */
        @Override
        public void receiveConfigInfo(final String content) {

            try {
                //刷新IP
                if (StringUtils.equals(content, GatewayConstants.IP)) {
                    cacheScheduled.reloadGatewayWhiteIps();
                }
                //刷新鉴权
                else if (StringUtils.equals(content, GatewayConstants.AUTH)) {
                    cacheScheduled.reloadGatewayAuth();
                }
                else if (StringUtils.equals(content, GatewayConstants.ROUTE)) {
                    cacheScheduled.reloadGatewayRoute();
                }
                configService.publishConfig(dataId, group, "ok");
            }catch (Exception e) {

                try {
                    configService.publishConfig(dataId, group, "failed");
                } catch (Exception e1) {
                    logger.error("刷新配置失败", e1);
                }
            }


        }
    }

}
