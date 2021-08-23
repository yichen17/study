package com.yichen.dao;

import com.yichen.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/23 11:01
 * @describe
 */
@Mapper
public interface SysUserDao {

    public User getUser(int id);

}
