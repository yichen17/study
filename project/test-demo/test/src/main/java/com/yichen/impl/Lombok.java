package com.yichen.impl;

import com.yichen.Log;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/23 14:32
 */
public class Lombok implements Log {
    @Override
    public void show(String info) {
        System.out.println("there is Lombok: "+info);
    }
}
