package com.yichen.jedisdemo.jedis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/5 9:49
 * @describe  jedis 连接池使用方式
 */
public class Main2 {
    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxTotal(18);
        JedisPool pool = new JedisPool(config, "127.0.0.1", 6379, 2000, "yichen");
        Jedis jedis = pool.getResource();
        String value = jedis.get("name");
        System.out.println(value);
        jedis.close();
        pool.close();
    }
}
