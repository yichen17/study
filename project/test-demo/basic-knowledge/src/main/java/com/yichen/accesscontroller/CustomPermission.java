package com.yichen.accesscontroller;

import java.security.BasicPermission;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/6/11 10:42
 * @describe 自定义permission
 */
public class CustomPermission extends BasicPermission {
    public CustomPermission(String name) {
        super(name);
    }

    public CustomPermission(String name, String actions) {
        super(name, actions);
    }
}
