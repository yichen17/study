package com.yichen.agent.jdkType;

import com.yichen.agent.jdkType.impl.RealSubject;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/2 10:40
 */
public class Main {
    public static void main(String[] args) {
        Subject subject=new RealSubject();
        MyInvocationHandler handler=new MyInvocationHandler(subject);
        Subject proxy= (Subject) handler.getProxy();
        System.out.println(proxy.sayHello("yichen"));
    }
}
