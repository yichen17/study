package server.demo.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/5 15:41
 * @describe 远程调用的接口，理论上应该是内网
 */
@RestController
@Slf4j
public class TestController {

    /**
     * 远程请求访问接口，模拟访问数据库返回数据
     * @return
     */
    @RequestMapping("/get")
    public String getData(){
        log.info("访问get接口");
        Map<String,Object> map=new HashMap<>();
        map.put("name","yichen");
        map.put("age",18);
        map.put("sex","girl");
        return JSON.toJSONString(map);
    }

}
