package com.yichen.appmvc.controller;

import com.yichen.appmvc.annotation.Describer;
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
import org.springframework.web.servlet.ModelAndView;

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
    @Describer(describe = "测试接口，用于展示jsp界面")
    public String test(){
        System.out.println("test ");
        return "base/modules/sys/hello";
    }

    @ResponseBody
    @RequestMapping(value = "/show")
    @Describer(describe = "展示接口，返回渲染界面表示请求成功")
    public ResultVo show(){
        logger.info("show()方法开始执行");
        return new ResultVo().setCode("1").setData(new String[]{"hello","world","show"});
    }

    @ResponseBody
    @RequestMapping(value = "/test/map",method = RequestMethod.POST)
    @Describer(describe = "测试使用统一结果集来渲染返回结果")
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
    @Describer(describe = "测试使用统一结果集来渲染返回结果")
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
    @Describer(describe = "通过stream 来筛选数据")
    public ResultVo getUsersGroupByName(){
        logger.info("请求方法getUsersGroupByName");
        Map<String, List<User>> users = userService.getUsersAndGroupByName();
        ResultVo resultVo= ResultUtils.successResult();
        resultVo.setData(users);
        resultVo.setCode("0");
        return resultVo;
    }

    /**
     * 测试 controller 层面转发
     * @return
     */
    @RequestMapping(value = {"/redirect","/redirect1"})
    @Describer(describe = "测试请求转发实现")
    public ModelAndView redirectTest(){
        logger.info("访问redirect方法，即将转发请求至show方法");
        ModelAndView mav=new ModelAndView();
        mav.setViewName("forward:show");
        return  mav;
    }





}
