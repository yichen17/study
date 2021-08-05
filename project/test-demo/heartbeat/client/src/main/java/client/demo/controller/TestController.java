package client.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/5 15:37
 * @describe 远程请求 controller ，单纯转发数据
 */
@RestController
public class TestController {

    @RequestMapping(value = "/get")
    public String getData(){
        return null;
    }

}
