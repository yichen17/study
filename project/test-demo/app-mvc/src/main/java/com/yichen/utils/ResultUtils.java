package com.yichen.utils;

import com.yichen.appmvc.vo.ResultVo;

import java.io.Serializable;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/7/30 9:38
 * @describe 结果工具类
 */
public class ResultUtils implements Serializable {

    public static ResultVo successResult(){
        return new ResultVo();
    }

}
