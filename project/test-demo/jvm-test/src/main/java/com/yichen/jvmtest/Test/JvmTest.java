package com.yichen.jvmtest.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/15 9:35
 * @describe 微信公众号  你假笨   不起眼，但是足以让你收获的JVM 内存案例
 */
public class JvmTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch=new CountDownLatch(500);
        JvmTest.TestClass[] classes=new TestClass[500];
        for(int i=0;i<500;i++){
            classes[i]=new TestClass(latch);
            classes[i].start();
        }
        TimeUnit.SECONDS.sleep(300L);
        for(int i=0;i<500;i++){
            classes[i].setStop();
        }
    }
    public static class TestClass extends Thread{
        CountDownLatch latch;
        ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
        boolean stop=false;

        public TestClass(CountDownLatch latch){
            this.latch=latch;
        }

        public void setStop(){
            this.stop=true;
        }
        @Override
        public void run(){
            while(!this.stop){
                try {
                    TimeUnit.MILLISECONDS.sleep(100L);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                byte[] buf=new byte[0];
            }
        }

    }

}
