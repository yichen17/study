package com.yichen.Annotation.Test;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/20 9:40
 */
@InheritedTest("使用Inherited的注解 class")
public class ParentX {

    @InheritedTest("使用Inherited的注解 method")
    public void method(){

    }
    @InheritedTest("使用Inherited的注解 method2")
    public void method2(){

    }

    @InheritedTest("使用Inherited的注解 field")
    public String a;
}
