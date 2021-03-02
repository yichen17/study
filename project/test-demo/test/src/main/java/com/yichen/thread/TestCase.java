package com.yichen.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/26 10:08
 */
public class TestCase{
    private volatile long startTime;
    private final  WorkCase workCase=new WorkCase();
    private final Thread workerThread;


    public TestCase(ThreadFactory factory) {
        workerThread=factory.newThread(workCase);
    }

    private final class WorkCase implements Runnable{
        @Override
        public void run() {
            startTime=System.nanoTime();
            System.out.println("WorkCase startTime: "+startTime);

        }


    }

    public void TestStartTime(){
//        此处虽然会更改 startTime 的值，但是由于线程是异步的，所以存在先后顺序问题
        start();
        System.out.println("TestCase startTime: "+startTime);
    }
    public void start(){
        workerThread.start();
    }

}
