package com.yichen.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/24 10:31
 */
public class PatternTest {
    private static final Pattern NAME_SEPARATOR=Pattern.compile("\\s*[,]+\\s*");

    public static void main(String[] args) {
        String[] names = NAME_SEPARATOR.split(",,");
        for(String name:names){
            System.out.println(name);
        }
        System.out.println("length:"+names.length);
//        Pattern p=Pattern.compile("[,.]*");
//        Matcher m=p.matcher(".");
//        boolean b=m.matches();
//        System.out.println(b);
    }
}
