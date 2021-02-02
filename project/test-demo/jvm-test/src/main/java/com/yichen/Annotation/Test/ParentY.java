package com.yichen.Annotation.Test;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/20 9:41
 */
@InheritedTest2("未使用Inherited的注解 class")
public class ParentY {

    @InheritedTest2("未使用Inherited的注解 method")
    public void method(){

    }
    @InheritedTest2("未使用Inherited的注解 method2")
    public void method2(){

    }

    @InheritedTest2("未使用Inherited的注解 field")
    public String a;
}
