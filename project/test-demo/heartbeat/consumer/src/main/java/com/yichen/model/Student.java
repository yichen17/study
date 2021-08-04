package com.yichen.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/4 16:48
 * @describe
 */
@Data
@ToString
public class Student implements Serializable {

    private String name;
    private String age;
    private String isSettle;

}
