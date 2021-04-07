package com.yichen.reactor.operator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/31 9:54
 * @describe  转换操作符
 */
public class TransformDemo {
    public static void main(String[] args) {
        Flux.range(1, 25).buffer(10).subscribe(System.out::println);

        Flux.range(1, 5).window(2).toIterable().forEach(w -> {
                w.subscribe(System.out::println);
                System.out.println("-------");
        });

        Flux.just(1, 2).map(i -> "number-" + i).subscribe(System.out::println);

        Flux.just(1, 5).flatMap(x -> Mono.just(x * x)).subscribe(System.out::println);
    }
}
