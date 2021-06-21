package com.yichen.mybatisgenerator.service;

import com.yichen.cook.food.mapper.foodCookStepsMapper;
import com.yichen.cook.food.model.foodCookSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/20 14:07
 */
@Service
public class testService {

    @Autowired
    private foodCookStepsMapper dao;

    public int add(foodCookSteps steps){
        return dao.insert(steps);
    }

    public foodCookSteps get(int id){
        return dao.selectByPrimaryKey(id);
    }

}
