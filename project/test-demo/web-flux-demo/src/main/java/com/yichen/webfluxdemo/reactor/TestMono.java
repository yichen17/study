package com.yichen.reactor;

import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/30 9:50
 */
public class TestMono {
    public static void main(String[] args) {
        Mono.justOrEmpty(Optional.of("jianxiang")).subscribe(System.out::println);

        Mono.create(sink ->
                sink.success("jianxiang")).subscribe(System.out::println);

    }
}
