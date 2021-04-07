package com.yichen.reactor.operator;

import reactor.core.publisher.Flux;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/6 10:12
 * @describe 条件操作符
 */
public class ConditionDemo {
    public static void main(String[] args) {
        //  获取直到条件满足
        Flux.range(1, 100).takeUntil(i -> i == 10).subscribe(System.out::println);
        // 满足条件才获取
        Flux.range(1, 100).takeWhile(i -> i <= 10).subscribe(System.out::println);
        Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
        Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);
    }
}
