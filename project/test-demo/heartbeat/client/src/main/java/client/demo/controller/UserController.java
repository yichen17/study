package client.demo.controller;


import client.demo.utils.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/12 9:20
 * @describe 用户相关的操作
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Value("${yichen.username}")
    private String username;
    @Value("${yichen.password}")
    private String password;



    @RequestMapping("/login")
    public ReturnT login(HttpServletRequest httpServletRequest,
                         @RequestParam("username") String username, @RequestParam("password")String password){
        log.info("请求ip地址为{},入参username:{},password:{}",httpServletRequest.getRemoteAddr(),username,password);
        if(this.username.equals(username)&&this.password.equals(password)){
            Map<String,String> data=new HashMap<>();
            data.put("secret","yichenshanliangz");
            ReturnT res=new ReturnT(data);
            return res;
        }
        return new ReturnT("1","用户名或密码错误");
    }

}
