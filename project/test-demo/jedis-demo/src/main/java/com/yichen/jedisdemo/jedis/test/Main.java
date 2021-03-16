package com.yichen.jedisdemo.jedis.test;

import com.yichen.jedisdemo.jedis.utils.Properties;
import redis.clients.jedis.Jedis;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/4 17:00
 */
public class Main {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);  //指定Redis服务Host和port
        jedis.auth("yichen"); //如果Redis服务连接需要密码，制定密码
        String value = jedis.get("name"); //访问Redis服务
        System.out.println(value);
        jedis.close(); //使用完关闭连接
    }

}
