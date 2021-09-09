package client.demo.controller;


import client.demo.utils.MapTools;
import client.demo.utils.ReturnT;
import com.yichen.utils.SecretUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/12 9:20
 * @describe 用户相关的操作
 */
@Controller
@Slf4j
public class UserController {

    @Value("${yichen.username}")
    private String username;
    @Value("${yichen.password}")
    private String password;

    @RequestMapping("/login")
    public String login(HttpServletRequest httpServletRequest){
        log.info("请求ip地址为{}",httpServletRequest.getRemoteAddr());
        return "login.jsp";
    }

    @RequestMapping("/hello")
    public String hello(HttpServletRequest httpServletRequest){
        log.info("请求ip地址为{}",httpServletRequest.getRemoteAddr());
        return "hello.html";
    }



    @RequestMapping("/loginCheck")
    public ReturnT loginCheck(HttpServletRequest httpServletRequest,
                         @RequestParam("username") String username, @RequestParam("password")String password){
        log.info("请求ip地址为{},入参username:{},password:{}",httpServletRequest.getRemoteAddr(),username,password);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try {
            subject.login(token);
            log.info("登陆成功");
            Map<String,String> data=new HashMap<>();
            String key=SecretUtils.constructPrivateKey();
            String encodeKey= SecretUtils.transform(key);
            data.put("secret",encodeKey);
            log.info("随机生成md5=key{},加密后的key{}",key,encodeKey);
            MapTools.md5_keys.put(httpServletRequest.getRemoteAddr(),encodeKey);
            ReturnT res=new ReturnT(data);
            return res;
        } catch (UnknownAccountException | IncorrectCredentialsException e) {
            return new ReturnT("1","用户名或密码错误");
        }
    }

}
