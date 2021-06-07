package com.yichen.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/6/7 9:27
 * @describe  测试线程中断。看源码的时候 中断一个一直执行的线程，都是先将循环条件置为 false，然后调用线程的interrupt()  方法，这里研究一下原因
 */
public class TestThreadStop {

    private static Logger logger= LoggerFactory.getLogger(TestThreadStop.class);
    private  static  boolean isStop=false;

    public static void main(String[] args){
        isStop=false;
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                int times=0;
                while(!isStop){
                    System.out.println("当前执行次数："+times++);
                    try{
                        Thread.sleep(500);
                    }
                    catch (Exception e){
                        logger.info("线程睡眠期间被中断："+e.getMessage(),e);
                    }
                }
            }
        },"test thread case");
        thread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isStop=true;
        if(thread.getState()!=Thread.State.TERMINATED){
            thread.interrupt();
            try{
                thread.join();
            }
            catch (InterruptedException e){
                logger.info(e.getMessage(),e);
            }
        }
        System.out.println("end");
    }

}
