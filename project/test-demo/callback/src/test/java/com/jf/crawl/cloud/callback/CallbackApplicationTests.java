package com.jf.crawl.cloud.callback;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CallbackApplicationTests {

    @Test
    public void contextLoads() throws InterruptedException {
        // 并发次数
        int testCount = 1000;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(testCount);
        ArrayList<Future<String>> results = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(testCount);
        for (int i = 0; i < testCount; i++) {
            executorService.execute(new MyThread(cyclicBarrier));
        }
        long startTime = System.currentTimeMillis();
        System.out.println("已经开启所有的子线程");
        executorService.shutdown();
        System.out.println("shutdown()：启动一次顺序关闭，执行以前提交的任务，但不接受新任务。");
        while (true) {
            if(executorService.isTerminated()){
                System.out.println("所有的子线程都结束了！");
                break;
            }
            Thread.sleep(1000);
        }
        long cost = System.currentTimeMillis() - startTime;
        System.out.println("所有任务执行完成:cost="+cost);
    }



}

