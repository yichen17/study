package com.yichen.agent.jdkType;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/2 10:36
 */
public class MyInvocationHandler implements InvocationHandler {
    private  Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("预处理");
        Object result=method.invoke(target,args);
        System.out.println("执行完毕");
        return result;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(),this);
    }

}
