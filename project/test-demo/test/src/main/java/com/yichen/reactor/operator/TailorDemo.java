package com.yichen.reactor.operator;

import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/6 10:17
 * @describe 裁剪操作符
 */
public class TailorDemo {
    public static void main(String[] args) {
        Flux.just(3, 5, 7, 9, 11, 15, 16, 17).any(e -> e % 2 == 0).subscribe(isExisted -> System.out.println(isExisted));
        Flux.just("abc", "ela", "ade", "pqa", "kang").all(a -> a.contains("a")).subscribe(isAllContained -> System.out.println(isAllContained));
        Flux.concat(Flux.range(1, 3),Flux.range(4, 2),Flux.range(6, 5)).subscribe(System.out::println);
        // 测试concat 的顺序问题    单纯按照流的顺序
        Flux.concat(Flux.just(1,3,5),Flux.just(7,4,2)).subscribe(System.out::println);
        // 测试concat 是否会根据创建的时间先后顺序进行合并    时间间隔生成只获得一开始的数据
        Flux.concat(Flux.interval(Duration.ofMillis(0), Duration.ofMillis(100)).take(2),
                Flux.interval(Duration.ofMillis(50), Duration.ofMillis(100)).take(2)).subscribe(System.out::println);
        Flux.range(1, 10).reduce((x, y) -> x + y).subscribe(System.out::println);
        //  reduceWith 指定初始值
        Flux.range(1, 10).reduceWith(() -> 5, (x, y) -> x + y).subscribe(System.out::println);
    }
}
