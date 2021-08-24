package com.yichen.webfluxdemo.exhibition;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;


/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/24 10:15
 * @describe 响应流实现运行中线程池的切换
 */
public class ThreadPoolSwitch {

    public static void main(String[] args) throws Exception{
        System.out.println("now Thread name "+Thread.currentThread().getName());
        Scheduler publish= Schedulers.newElastic("thread-publishOn");
        Scheduler subscribe=Schedulers.newElastic("thread-subscribeOn");
        Flux.just("tom")
                .map(s -> {
                    System.out.println("[map] Thread name: " + Thread.currentThread().getName());
                    return s.concat("@mail.com");
                })
                .publishOn(publish)
                .filter(s -> {
                    System.out.println("[filter] Thread name: " + Thread.currentThread().getName());
                    return s.startsWith("t");
                })
                .subscribeOn(subscribe)
                .subscribe(s -> {
                    System.out.println("[subscribe] Thread name: " + Thread.currentThread().getName());
                    System.out.println(s);
                });
        Thread.sleep(500);
        // 释放它们，不然一直运行中。。。
        publish.dispose();
        subscribe.dispose();
    }

}
