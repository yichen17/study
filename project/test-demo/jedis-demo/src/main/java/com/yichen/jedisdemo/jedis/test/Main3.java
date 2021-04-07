package com.yichen.jedisdemo.jedis.test;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/5 10:50
 */
public class Main3 {
    public static void main(String[] args) {
        //  ec5f771c17a549f9c930e46bfd4c0bdd
        String value=DigestUtils.md5Hex("renhaoshanliang28");
        System.out.println(value);
    }
}
