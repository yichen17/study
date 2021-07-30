package com.yichen.appmvc.service.impl;

import com.yichen.appmvc.dao.UserMapper;
import com.yichen.appmvc.model.User;
import com.yichen.appmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/7/30 9:04
 * @describe
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        return userMapper.getUsers();
    }
    @Override
    public Map<String,List<User>> getUsersAndGroupByName(){
        Map<String,List<User>> map=new HashMap<>(3);
        List<User> users = userMapper.getUsers();
        Set<String> names=new HashSet<>();
        users.stream().forEach(a->names.add(a.getName()));
        Iterator<String> iterator=names.iterator();
        for(String name:names){
            List<User> data=new ArrayList<>(3);
            users.stream().filter(user -> user.getName().equals(name)).forEach(data::add);
            map.put(name,data);
        }
        return map;
    }

}
