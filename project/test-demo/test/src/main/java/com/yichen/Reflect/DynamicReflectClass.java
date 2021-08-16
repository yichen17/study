package com.yichen.Reflect;

import org.springframework.asm.ClassVisitor;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.core.ClassGenerator;
import org.springframework.cglib.core.DefaultGeneratorStrategy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.transform.impl.AddPropertyTransformer;

import java.io.Serializable;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/16 17:26
 * @describe 根据规则动态创建类
 *
 */
public class DynamicReflectClass {

    public static void  construct(String s){
        Enhancer e = new Enhancer();
        e.setSuperclass(Object.class);
        e.setInterfaces(new Class[]{Serializable.class});

        BeanGenerator beanGenerator=new BeanGenerator();
        String [] fields=s.split(",");
        for(String field:fields){
            beanGenerator.addProperty(field,String.class);
        }
        beanGenerator.generateClass();

        Object obj = e.create();
    }

}
