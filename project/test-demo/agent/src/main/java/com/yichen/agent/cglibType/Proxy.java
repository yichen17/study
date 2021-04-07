package com.yichen.agent.cglibType;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/2 16:38
 */
public class Proxy implements MethodInterceptor {

    private Enhancer enhancer=new Enhancer();

    public Object getProxy(Class clazz){
        enhancer.setCallback(this);
        enhancer.setSuperclass(clazz);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("前置处理");
        Object result = proxy.invokeSuper(obj, args); // 调用父类中的方法
        System.out.println("后置处理");
         return result;
    }
}
