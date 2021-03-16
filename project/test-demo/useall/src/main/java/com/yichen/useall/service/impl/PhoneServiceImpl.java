package com.yichen.useall.service.impl;

import com.yichen.useall.bean.Phone;
import com.yichen.useall.dao.PhoneLocationDao;
import com.yichen.useall.service.BaseService;
import org.nutz.ssdb4j.spi.SSDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/11 14:57
 */
@Service
public class PhoneServiceImpl implements BaseService {

    @Autowired
    private PhoneLocationDao phoneLocationDao;



    public String getLocationByPhone(String phone){
        Phone p= phoneLocationDao.selectByPhone(phone);
        return p.getProvince()+"-"+p.getCity()+"-"+p.getIsp();
    }

}
