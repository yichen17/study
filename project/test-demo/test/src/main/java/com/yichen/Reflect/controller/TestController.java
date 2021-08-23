package com.yichen.Reflect.controller;

import com.yichen.dao.SysUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/23 10:54
 * @describe
 */
@RestController(value = "testController1")
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SysUserDao sysUserDao;


    @RequestMapping("/show")
    public String show(){
        return sysUserDao.getUser(1).toString();
    }

    @RequestMapping("/say")
    public String say(){
        return "hello";
    }

}
