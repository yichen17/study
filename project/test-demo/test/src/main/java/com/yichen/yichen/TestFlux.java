package com.yichen.yichen;

import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/3/30 9:32
 */
public class test {
    public static void main(String[] args) {
//        静态创建
        Flux.just("Hello", "World").subscribe(System.out::println);
        Flux.fromArray(new Integer[] {1, 2, 3}).subscribe(System.out::println);
        Flux.range(2020,5).subscribe(System.out::println);
        Flux.interval(Duration.ofSeconds(2), Duration.ofMillis(200)).subscribe(System.out::println);
        Flux.empty().subscribe(System.out::println);
//        动态创建
//        generate  需要 synchronousSink组件
        Flux.generate(sink -> {
            sink.next("Jianxiang");
            sink.complete();
        }).subscribe(System.out::println);

        Flux.generate(() -> 1, (i, sink) -> {
            sink.next(i);
            if (i == 5) {
                sink.complete();
            }
            return ++i;
        }).subscribe(System.out::println);
//   create  需要 FluxSink组件
        Flux.create(sink -> {
            for (int i = 0; i < 5; i++) {
                sink.next("jianxiang" + i);
            }
            sink.complete();
        }).subscribe(System.out::println);

    }
}
