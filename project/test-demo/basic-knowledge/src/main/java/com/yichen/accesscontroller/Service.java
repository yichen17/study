package com.yichen.accesscontroller;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/6/11 10:42
 */
public class Service {

    public static final String OPERATION = "my-operation";

    public void operation() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new CustomPermission(OPERATION));
        }
        System.out.println("Operation is executed");
    }
}
