package com.yichen.appmvc.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/3 8:54
 * @describe controller 中的  mapping 描述
 */
@Data
@ToString
public class MethodDescribe implements Serializable {
    /**
     * 描述，介绍方法用途
     */
    private String describe;
    /**
     * 方法的请求url
     */
    private String methodUrl;
    /**
     * 请求方式
     */
    private String type;
    /**
     * 方法所在的控制器
     */
    private String controller;
    /**
     * 控制器的请求路径
     */
    private String controllerUrl;

}
