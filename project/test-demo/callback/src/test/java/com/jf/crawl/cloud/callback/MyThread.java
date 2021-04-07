package com.jf.crawl.cloud.callback;

import java.util.concurrent.CyclicBarrier;

public class MyThread implements Runnable{
    private CyclicBarrier cyclicBarrier;
    public MyThread(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public void run() {
        try{
            // 等待所有任务准备就绪
            cyclicBarrier.await();
            HttpClientPost.postUrl();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
