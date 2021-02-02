package com.yichen.Test;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/25 16:07
 */
public class Child extends Parent{
    @Override
    public String Show(){
        return "there is child show";
    }

    public String Print(){
        return super.see();
    }
}
