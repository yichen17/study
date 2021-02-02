package com.yichen.Test;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/25 16:07
 */
public class Parent {
    public String Show(){
        return "there is parent show";
    }

    public String see(){
        return Show();
    }

}
