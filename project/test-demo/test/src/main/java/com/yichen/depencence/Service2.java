package com.yichen.depencence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/26 11:09
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class Service2 {
    @Autowired
    private Service1 service1;
//    @Async
//    public void test2(){
//
//    }
}
