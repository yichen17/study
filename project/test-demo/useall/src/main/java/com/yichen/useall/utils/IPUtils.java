package com.yichen.useall.utils;

import java.net.InetAddress;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/15 11:28
 */
public class IPUtils {
    public static String getLocalIp(){
        try {
            InetAddress ia = InetAddress.getLocalHost();
            return ia.getHostAddress();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
