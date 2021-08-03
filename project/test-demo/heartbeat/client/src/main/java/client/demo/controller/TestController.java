package client.demo.controller;

import client.demo.model.ReturnT;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/3 16:10
 * @describe
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public ReturnT test(){
        return new ReturnT<String>(ReturnT.SUCCESS_CODE, "远程调用成功");
    }

    @RequestMapping("/t")
    @ResponseBody
    public String t(){
        return "test";
    }


}
