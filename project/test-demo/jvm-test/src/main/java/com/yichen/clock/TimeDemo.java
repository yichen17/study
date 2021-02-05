package com.yichen.clock;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/5 10:38
 */
public class TimeDemo {
    private static class MyTask extends TimerTask{
        @Override
        public void run() {
            System.out.println("hello world");
        }
    }
    public static void main(String[] args) {
        Timer timer=new Timer();
        timer.schedule(new MyTask(),3000,10000);
    }
}
