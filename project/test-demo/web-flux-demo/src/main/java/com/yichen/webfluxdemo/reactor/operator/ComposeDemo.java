package com.yichen.reactor.operator;

import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/31 10:06
 * @describe 组合操作符
 */
public class ComposeDemo {
    public static void main(String[] args) {
        Flux.just(1,2,3).then().subscribe(System.out::println);
        Flux.just(1,2,3).thenMany(Flux.just(4,5)).subscribe(System.out::println);

        Flux.merge(Flux.interval(Duration.ofMillis(0), Duration.ofMillis(100)).take(2),
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(2)).toStream().forEach(System.out::println);

        Flux.mergeSequential(Flux.interval(Duration.ofMillis(0), Duration.ofMillis(100)).take(2),
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(2)).toStream().forEach(System.out::println);

        Flux.just(1, 2).zipWith(Flux.just(3, 4))
                .subscribe(System.out::println);

        Flux flux1 = Flux.just(1, 2);
        Flux flux2 = Flux.just(3, 4);
        Flux.zip(flux1, flux2).subscribe(System.out::println);
    }
}
