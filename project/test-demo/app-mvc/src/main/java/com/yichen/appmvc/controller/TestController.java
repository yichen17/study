package com.yichen.appmvc.controller;

import com.yichen.appmvc.model.User;
import com.yichen.appmvc.service.UserService;
import com.yichen.appmvc.vo.ResultVo;
import com.yichen.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/16 15:47
 */
@Controller
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        System.out.println("test ");
        return "base/modules/sys/hello";
    }

    @ResponseBody
    @RequestMapping(value = "/test/map",method = RequestMethod.POST)
    public ResultVo testMap(){
        ResultVo resultVo=new ResultVo();
        Map<String ,String> data=new HashMap<>();
        data.put("name","yichen");
        data.put("age","18");
        resultVo.setCode("0");
        resultVo.setData(data);
        return resultVo;
    }

    @ResponseBody
    @RequestMapping(value = "/getUsers")
    public ResultVo getUsers(){
        logger.info("请求方法getUsers");
        List<User> users= userService.getUsers();
        logger.info("查询结果集{}", users);
        ResultVo resultVo= ResultUtils.successResult();
        resultVo.setData(users);
        resultVo.setCode("0");
        return resultVo;
    }

    @ResponseBody
    @RequestMapping(value = "/getUsersGroupByName")
    public ResultVo getUsersGroupByName(){
        logger.info("请求方法getUsersGroupByName");
        Map<String, List<User>> users = userService.getUsersAndGroupByName();
        ResultVo resultVo= ResultUtils.successResult();
        resultVo.setData(users);
        resultVo.setCode("0");
        return resultVo;
    }





}
