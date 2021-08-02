package com.yichen.appmvc.task;

import org.springframework.stereotype.Component;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/2 11:16
 * @describe 心跳任务
 */
@Component
public class HeartBeatTask implements Runnable{

    /**
     * 这里需要 创建 netty 监听端口，收到请求后维持心跳保持长连接
     * 之后根据请求内容分发： 1、正常心跳  2、请求访问需转发  3、
     * 可用性， 连接不可用时改进版二进制指数退避算法
     * 由于部署在内网，所以需要优先访问公网来建立连接而不是公网来访问我们
     */

    private final static String HEART_HEAT_URL="";

    @Override
    public void run() {
        try{
//            URL url=new URL(HEART_HEAT_URL);
//            URLConnection connection = url.openConnection();
//            connection.setUseCaches(true);
//            connection.connect();

        }
        catch (Exception e){

        }

    }
}
