package com.yichen.reactor.operator;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/6 10:34
 * @describe  工具操作符
 */
public class ToolsDemo {
    public static void main(String[] args) {

        // 自定义 subscriber
        Subscriber<String> subscriber = new Subscriber<String>() {
            volatile Subscription subscription;
            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                System.out.println("initialization");
                subscription.request(1);
            }
            @Override
            public void onNext(String s) {
                System.out.println("onNext:" + s);
                subscription.request(1);
            }
            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
            @Override
            public void onError(Throwable t) {
                System.out.println("onError:" + t.getMessage());
            }
        };
        Flux.just("12", "23", "34").subscribe(subscriber);
        Flux.just(1, 2).log().subscribe(System.out::println);
        Mono.just(1).map(x -> 1 / x).checkpoint("debug").subscribe(System.out::println);
    }
}

//  不推荐自己通过new 创建自定义 subscriber 而是 通过  扩展 Project Reactor 提供的 BaseSubscriber 类

//class MySubscriber<T> extends BaseSubscriber<T> {
//    @Override
//    public void hookOnSubscribe(Subscription subscription) {
//        System.out.println("initialization");
//        request(1);
//    }
//
//    @Override
//    public void hookOnNext(T value) {
//        System.out.println("onNext:" + value);
//        request(1);
//    }
//}