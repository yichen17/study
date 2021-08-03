package com.yichen.appmvc.config;

import com.yichen.appmvc.annotation.Describer;
import com.yichen.appmvc.model.MethodDescribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/2 14:57
 * @describe 获取所有 载入 spring 容器的 bean
 */
@Component
public class BeanFilter implements ApplicationContextAware {

    /**
     * 保存 controller 中的可访问路径相关信息，用于连接的时候发送可用列表
     */
    public static List<MethodDescribe> describes=new ArrayList<>();
    /**
     * 请求路径查询，通过请求的方法名称查询请求地址。
     */
    public static Map<String,String> requestUrlMap=new HashMap<>();

    private static final Logger logger=LoggerFactory.getLogger(BeanFilter.class);

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
            // 获取controller下的类
            if(beanType.getPackage().getName().equals("com.yichen.appmvc.controller")){
                // 获取 controller 中的方法，所有方法
                Method[] methods = beanType.getMethods();
                // 同一个controller，这两个属性一样。
                String packageUrl=beanType.getPackage().getName()+beanType.getName();
                String controllerUrl=beanType.getAnnotation(RequestMapping.class)==null
                        ?"":Arrays.toString(beanType.getAnnotation(RequestMapping.class).value());
                for(Method method:methods){
                    // 判断方法上的注解
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    if(annotation!=null){
                        logger.info("筛选出Controller注解的bean对象，controller名称{},内容是{}",beanType.getName(),annotation);
                        MethodDescribe methodDescribe=new MethodDescribe();
                        // 可以为数组，需要进行判断
                        methodDescribe.setMethodUrl(Arrays.toString(annotation.value()));
                        methodDescribe.setType(annotation.method().length==0?"POST":Arrays.toString(annotation.method()));
                        Describer describer=method.getAnnotation(Describer.class);
                        // 切分包的路径 url，最后一部分即为controller

                        methodDescribe.setController(packageUrl);
                        // 可以为数组，需要进行判断   可能存在没有该注解的情况
                        methodDescribe.setControllerUrl(controllerUrl);
                        if(describer==null){
                            methodDescribe.setDescribe(null);
                        }
                        else{
                            methodDescribe.setDescribe(describer.describe());
                        }
                        describes.add(methodDescribe);
                        // TODO 方法同名复用情况    路径 笛卡尔积问题
                        requestUrlMap.put(packageUrl+method.getName(),"");
                    }
                }
            }
//            System.out.println("BeanName:" + beanName);
//            System.out.println("Bean的类型：" + beanType);
//            System.out.println("Bean所在的包：" + beanType.getPackage());
//            System.out.println("Bean：" + BeanFilter.getApplicationContext().getBean(
//                    beanName));
        }
        System.out.println("=======================================================================================");
        System.out.println(describes);
    }
}
