package com.yichen.appmvc.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/2 14:57
 * @describe 获取所有 载入 spring 容器的 bean
 */
@Component
public class BeanFilter implements ApplicationContextAware {



    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        BeanFilter.applicationContext = applicationContext;
        printAllBeans();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 测试获取所有的 bean ，同时过滤获得 自定义的 controller，构造请求列表
     */
    public static void printAllBeans() {

        String[] beans = BeanFilter.getApplicationContext()
                .getBeanDefinitionNames();
        for (String beanName : beans) {
            Class<?> beanType = BeanFilter.getApplicationContext()
                    .getType(beanName);
            // 表示 controller 类型的bean
            if(beanType.getPackage().toString().equals("com.yichen.appmvc.controller")){
                // 获取 controller 中的方法，所有方法
                Method[] methods = beanType.getMethods();
                for(Method method:methods){
                    // 判断方法上的注解
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    if(annotation!=null){

                    }
                }
            }

//            System.out.println("BeanName:" + beanName);
//            System.out.println("Bean的类型：" + beanType);
//            System.out.println("Bean所在的包：" + beanType.getPackage());
//            System.out.println("Bean：" + BeanFilter.getApplicationContext().getBean(
//                    beanName));
        }
    }
}
