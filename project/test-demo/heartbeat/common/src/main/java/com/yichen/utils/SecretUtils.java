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
            key[i]=(char)(System.currentTimeMillis()%100+ random.nextInt(20));
        }
        log.info("生成md5-key为{}", Arrays.toString(key));
        return String.valueOf(key);
    }

    /**
     * 将MD5的key进行加密
     * @param value MD5的key
     * @return 加密后的MD5 key
     */
    public static String transform(String value){
        log.info("密钥转换入参{}",value);
        if(value==null||value.length()!=16){
            log.warn("密钥入参不符合要求");
            return "aaaaaatttttyyyyy";
        }
        else{
            StringBuilder builder=new StringBuilder();
            builder.append(value.substring(8,12)).append(value.substring(0,4))
                    .append(value.substring(12,16)).append(value.substring(4,8));
            for(int i=0,size=builder.length();i<size;i++){
                builder.setCharAt(i,(char) (builder.charAt(i)-REDUCE_NUM[i]));
            }
            log.info("转换后的密钥{}",builder.toString());
            return builder.toString();
        }
    }




}
