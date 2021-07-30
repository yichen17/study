package com.yichen.appmvc.service;

import com.yichen.appmvc.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/7/30 9:03
 * @describe
 */
public interface UserService {

    List<User> getUsers();

    /**
     * 将结果集 根据 name 进行分组返回。
     * @return
     */
    Map<String,List<User>> getUsersAndGroupByName();

}
