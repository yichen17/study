package server.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import server.demo.model.ReturnT;
import server.demo.utils.RemoteUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/3 15:33
 * @describe
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    private static Logger logger= LoggerFactory.getLogger(OrderController.class);

    private int times=0;

    public static String remoteUrl=null;

    @RequestMapping("/url")
    @ResponseBody
    public ReturnT<String> api(HttpServletRequest request) {


        logger.info("remotePort:{}",request.getRemotePort());
        remoteUrl=request.getRemoteAddr()+":"+request.getRemotePort();
        return new ReturnT<String>(ReturnT.SUCCESS_CODE, "success"+times++);

    }

    @RequestMapping("/get")
    @ResponseBody
    public ReturnT get(){
        logger.info("远程服务器地址{}",remoteUrl);
        return RemoteUtils.postBody(remoteUrl+"/test", "", 3, null, String.class);
    }

    @RequestMapping("/t")
    @ResponseBody
    public String test(){
        return "test";
    }


}
