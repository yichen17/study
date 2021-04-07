package com.yichen.Annotation.Test;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/19 18:03
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InheritedTest2 {

    String value();
}
