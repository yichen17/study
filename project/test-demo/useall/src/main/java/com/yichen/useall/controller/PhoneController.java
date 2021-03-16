package com.yichen.useall.controller;

import com.yichen.useall.dao.PhoneLocationDao;
import com.yichen.useall.service.BaseService;
import com.yichen.useall.service.impl.PhoneServiceImpl;
import com.yichen.useall.utils.IPUtils;
import org.nutz.ssdb4j.spi.SSDB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/11 14:43
 */
@RestController
public class PhoneController {

    private static Logger logger= LoggerFactory.getLogger(PhoneController.class);


    @Autowired
    private PhoneServiceImpl phoneService;

    @GetMapping("/test")
    public String Test(){
        return "test ok";
    }

    @GetMapping("/getLocationByPhone")
    public String getLocationByPhone(String phone){
        logger.info("请求getLocationByPhone 接口，入参手机号为："+phone);
        return phoneService.getLocationByPhone(phone);
    }

    @GetMapping("/getip")
    public String getLocalIP(){
        return IPUtils.getLocalIp();
    }

}
