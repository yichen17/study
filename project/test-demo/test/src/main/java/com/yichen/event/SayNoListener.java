package com.yichen.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/10/9 15:18
 * @describe 监听器
 */
@Component
@Order(5)
public class SayNoListener implements ApplicationListener<SayNoEvent> {

    private static Logger logger= LoggerFactory.getLogger(SayNoListener.class);

    @Override
    public void onApplicationEvent(SayNoEvent event) {
        logger.info(">>> say no {}",event.getName());
    }
}
