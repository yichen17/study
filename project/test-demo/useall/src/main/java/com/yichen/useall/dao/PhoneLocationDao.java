package com.yichen.useall.dao;

import com.yichen.useall.bean.PhoneLocation;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/11 14:30
 */
//@Mapper
public interface PhoneLocationDao {
    PhoneLocation selectByPhone(String phone);
}
