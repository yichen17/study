package com.yichen.thread;

import java.util.concurrent.ThreadFactory;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/26 10:06
 */
public class SimpleThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}
