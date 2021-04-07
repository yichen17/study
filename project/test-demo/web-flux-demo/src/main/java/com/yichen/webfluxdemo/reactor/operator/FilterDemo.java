package com.yichen.reactor.operator;

import reactor.core.publisher.Flux;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/31 10:03
 * @describe 过滤操作符
 */
public class FilterDemo {
    public static void main(String[] args) {
        Flux.range(1, 10).filter(i -> i % 2 == 0).subscribe(System.out::println);

        Flux.range(1, 100).take(10).subscribe(System.out::println);
        Flux.range(1, 100).takeLast(10).subscribe(System.out::println);

    }
}
