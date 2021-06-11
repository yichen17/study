package com.yichen.accesscontroller;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/6/11 9:57
 * @describe  研究 accessController
 */
public class TestAccessController {

    public static void main(String[] args)throws Exception {
        System.setSecurityManager(new SecurityManager());
//        new URL("http://www.google.com").openConnection().connect();
        Service service=new Service();
        service.operation();
    }

}
