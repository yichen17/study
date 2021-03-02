package com.yichen.agent.jdkType.impl;

import com.yichen.agent.jdkType.Subject;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/2 10:41
 */
public class RealSubject implements Subject {
    @Override
    public String sayHello(String name) {
        return name+":  hello";
    }
}
