package com.yichen.mybatisgenerator.controller;

import com.yichen.cook.food.model.foodCookSteps;
import com.yichen.mybatisgenerator.service.testService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/20 14:06
 */
@Controller
public class testController {

    @Autowired
    private testService service;

    @RequestMapping("/add")
    @ResponseBody
    public String add(@RequestBody foodCookSteps steps){
        service.add(steps);
        return "add success";
    }
}
