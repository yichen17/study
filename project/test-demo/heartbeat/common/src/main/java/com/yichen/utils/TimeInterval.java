package com.yichen.utils;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/11 9:08
 * @describe 时间间隔类，指数退避算法
 */
public class TimeInterval {

    /**
     * 最大间隔为3600000ms，即1小时
     */
    private static final int MAX_INTERNAL=3600000;

    /**
     * 指数退避算法计算重连时间
     * @param now 当前时间
     * @param max 自定最大时间
     * @return 下一次间隔时间
     */
    public static int getNextInternal(int now,int max){
        if(max==-1){
            max=MAX_INTERNAL;
        }
        now=now*2;
        return Math.min(max,now);
    }


}
