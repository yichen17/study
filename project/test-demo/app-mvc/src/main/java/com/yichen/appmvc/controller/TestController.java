package com.yichen.appmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/16 15:47
 */
@Controller
public class TestController {

    /**
     * 存在问题，按ctrl 可以跳转到对应的jsp，但是项目运行时controller 无法实现跳转
     * @return
     */
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        System.out.println("test ");
        return "base/modules/sys/hello";
    }

}
