package com.yichen.useall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yichen.useall.bean.PhoneLocation;
import com.yichen.useall.bean.PhoneLog;
import com.yichen.useall.dao.PhoneLocationDao;
import com.yichen.useall.dao.PhoneLogDao;
import com.yichen.useall.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/11 14:57
 */
@Service
public class PhoneServiceImpl implements BaseService {

    private static Logger logger= LoggerFactory.getLogger(PhoneServiceImpl.class);

    @Autowired
    private PhoneLocationDao phoneLocationDao;

    @Autowired
    private PhoneLogDao phoneLogDao;



    public String getLocationByPhone(String phone){
        String phone_pre=phone.substring(0,7);
        logger.info("执行手机号归属地查询，入参："+phone_pre);
        PhoneLocation p= phoneLocationDao.selectByPhone(phone_pre);
        logger.info("查询结果： "+p);
        PhoneLog log=new PhoneLog();
        if(p!=null){
            logger.info("查询命中，开始存入日志");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date=sdf.format(new Date());
            log.setDate(date);
            log.setPhone(phone);
            log.setState(0);
            log.setResult(JSONObject.toJSONString(p));
        }
        phoneLogDao.insertLog(log);
        logger.info("查询日志插入数据库成功");
        return p.getProvince()+"-"+p.getCity()+"-"+p.getIsp();
    }

}
