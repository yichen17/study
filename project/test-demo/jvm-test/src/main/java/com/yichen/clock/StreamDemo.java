package com.yichen.clock;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/5 14:07
 */
public class StreamDemo {
    private static final String [] WORD={"hello","hi","name","height","exciting"};
    public static void main(String[] args) {
        System.out.println(getLongestWord());
        System.out.println(getAllCharacter());

    }
    public static String getLongestWord(){
        Optional<String> result= Arrays.stream(WORD).reduce((a,b)->a.length()>=b.length()?a:b);
        return result.get();
    }

    public static Integer getAllCharacter(){
        Integer result=Arrays.stream(WORD).reduce(0,(a,b)->a+b.length(),(a,b)->a+b);
        return result;
    }

}
