package com.yichen.event;

import com.yichen.TestApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/10/9 15:37
 * @describe  测试 instance of
 */
public class TestInstance {
    public static void main(String[] args) {
        SayNoEvent event=new SayNoEvent(new Object(),"yichen");
        if(event instanceof  ApplicationEvent){
            System.out.println("ok");
        }
    }
}
