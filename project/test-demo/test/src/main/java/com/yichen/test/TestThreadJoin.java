package com.yichen.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/6/7 14:14
 * @describe  测试  thread 线程的  join方法。
 *    threadA.join()方法执行结束之后，主线程之后的内容都不会执行，这里存在一个 happen-before原则
 *    All actions in a thread happen-before any other thread successfully returns from a join() on that thread.
 */
public class TestThreadJoin {

    private static Logger logger= LoggerFactory.getLogger(TestThreadJoin.class);
    private static boolean isStopA=false;
    private static boolean isStopB=false;

    public static void main(String[] args) {
        Thread threadA=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    System.out.println("stopA......");
                    try{
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e){
                        logger.error("线程A"+e.getMessage(),e);
                    }
                }
            }
        });
        threadA.start();
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            logger.error("主线程"+e.getMessage(),e);
        }
        System.out.println("pre");

        try{
            threadA.join();
        }catch (InterruptedException e){
            logger.error(e.getMessage(),e);
        }
        System.out.println("after");


    }

}
