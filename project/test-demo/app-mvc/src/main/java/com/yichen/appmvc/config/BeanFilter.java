package com.yichen.appmvc.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/2 14:57
 * @describe 获取所有 载入 spring 容器的 bean
 */
public class BeanFilter implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        BeanFilter.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void printAllBeans() {
        String[] beans = BeanFilter.getApplicationContext()
                .getBeanDefinitionNames();
        for (String beanName : beans) {
            Class<?> beanType = BeanFilter.getApplicationContext()
                    .getType(beanName);
            System.out.println("BeanName:" + beanName);
            System.out.println("Bean的类型：" + beanType);
            System.out.println("Bean所在的包：" + beanType.getPackage());
            System.out.println("Bean：" + BeanFilter.getApplicationContext().getBean(
                    beanName));
        }
    }
}
