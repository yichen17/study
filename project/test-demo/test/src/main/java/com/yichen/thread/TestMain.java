package com.yichen.thread;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/2/26 10:07
 */
public class TestMain {
    public static void main(String[] args) {
        TestCase testCase=new TestCase(new SimpleThreadFactory());
        testCase.TestStartTime();
    }
}
