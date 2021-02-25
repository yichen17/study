package com.yichen;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/23 14:33
 */
public class Main {
    public static void main(String[] args) {
        ServiceLoader<Log> serviceLoader=ServiceLoader.load(Log.class);
        Iterator<Log> iterator=serviceLoader.iterator();
        while (iterator.hasNext()){
            Log log=iterator.next();
            log.show("jdk spi");
        }
    }
}
