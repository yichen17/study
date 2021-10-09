package com.yichen.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/10/9 15:16
 * @describe 自定义事件
 */
public class SayNoEvent extends ApplicationEvent {

    private String name;

    public SayNoEvent(Object source,String name) {
        super(source);
        this.name=name;
    }

    public String getName(){
        return name;
    }

}
