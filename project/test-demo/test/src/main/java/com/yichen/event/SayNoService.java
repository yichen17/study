package com.yichen.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/10/9 15:21
 * @describe service 发送事件
 */
@Service
public class SayNoService implements ApplicationContextAware, ApplicationEventPublisherAware {

    private ApplicationContext applicationContext;

    private ApplicationEventPublisher applicationEventPublisher;

    public boolean sayNo(String name){
        System.out.println("send event to say no , name is "+name);
        SayNoEvent sayNoEvent=new SayNoEvent(this,name);
        applicationContext.publishEvent(sayNoEvent);
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher=applicationEventPublisher;
    }
}
