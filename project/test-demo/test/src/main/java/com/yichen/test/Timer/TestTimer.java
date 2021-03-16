package com.yichen.test.Timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/16 11:08
 */
@Component
public class TestTimer {

    @Scheduled(cron = "0 * * * * ?")
    public void show(){
        System.out.println("now time is "+System.nanoTime());
        System.out.println(TimeZone.getDefault());
    }

}
