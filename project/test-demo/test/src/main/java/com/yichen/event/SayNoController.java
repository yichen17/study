package com.yichen.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/10/9 15:57
 * @describe say no 外部调用 发送事件
 */
@Controller
public class SayNoController {

    @Autowired
    private SayNoService sayNoService;

    @RequestMapping("/say/no")
    @ResponseBody
    public String sayNo(){
        sayNoService.sayNo("yichen");
        return "ooooooooooook";
    }

}
