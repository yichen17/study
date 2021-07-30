package com.yichen.appmvc.dao;

import com.yichen.appmvc.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/7/30 9:07
 * @describe
 */
@Mapper
public interface UserMapper {
    public List<User> getUsers();
}
