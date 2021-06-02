package com.yichen.test;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/6/2 15:39
 * @describe  测试 debug 时在指定 值停止供监控
 *    条件 设置需要双==，判断的是boolean 类型的值。如果直接在选项more中设置跳过次数
 */
public class TestCircleDebug {
    public static void main(String[] args) {
        int f[]={1,2,3,4,5};
        for(int i=0;i<f.length;i++){
            System.out.println(i);
        }
    }
}
