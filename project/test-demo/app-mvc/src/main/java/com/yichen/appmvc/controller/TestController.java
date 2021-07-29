package com.yichen.appmvc.controller;

import com.yichen.appmvc.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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




}
