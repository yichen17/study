package com.yichen.agent.cglibType;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/2 16:42
 */
public class Test {
    public String show(String s ){
        return s+":  show";
    }

    public static void main(String[] args) {
        Proxy proxy=new Proxy() ;
        Test test = (Test) proxy.getProxy(Test.class);
        System.out.println("预处理");
        System.out.println(test.show("yichen"));
        System.out.println("运行中止");
    }

}
