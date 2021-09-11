package com.yichen.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/12 10:11
 * @describe 密钥转换
 */
@Slf4j
public class SecretUtils {

    private static final int [] REDUCE_NUM={7,4,2,1,9,9,9,8,4,5,7,2,1,1,0,0};

    /**
     * 随机生成MD5的key
     * @return 随机生成的16位的 key
     */
    public static String constructPrivateKey(){
        char[]key=new char[16];
        Random random=new Random();
        for(int i=0;i<16;i++){
            key[i]=(char)((System.currentTimeMillis()%100+ random.nextInt(10))%26+65);
        }
        log.info("生成md5-key为{}", Arrays.toString(key));
        return String.valueOf(key);
    }
}
