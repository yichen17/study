package com.yichen.useall.controller;

import com.yichen.useall.dao.PhoneLocationDao;
import com.yichen.useall.service.BaseService;
import com.yichen.useall.service.impl.PhoneServiceImpl;
import org.nutz.ssdb4j.spi.SSDB;
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


    @Autowired
    private PhoneServiceImpl phoneService;

    @GetMapping("/test")
    public String Test(){
        return "test ok";
    }

    @GetMapping("/getLocationByPhone")
    public String getLocationByPhone(String phone){
        System.out.println("-----getLocationByPhone----");
        return phoneService.getLocationByPhone(phone);
    }

}
