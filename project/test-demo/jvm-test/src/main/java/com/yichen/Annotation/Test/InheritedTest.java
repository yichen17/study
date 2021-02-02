package com.yichen.Annotation.Test;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/1/19 18:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited

public @interface InheritedTest {

    String value();
}
