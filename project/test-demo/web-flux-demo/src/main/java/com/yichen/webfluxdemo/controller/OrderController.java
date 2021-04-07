package com.yichen.webfluxdemo.controller;

import com.yichen.webfluxdemo.entity.Order;
import com.yichen.webfluxdemo.service.StubOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/7 14:27
 * @describe  RESTful(Representational State Transfer，表述性状态转移)    实现webFlux
 */
@RestController
@RequestMapping(value="orders")
public class OrderController {

    private static Logger logger=LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private StubOrderService orderService;

    @GetMapping("")
    public Flux<Order> getOrders() {
        return this.orderService.getOrders();
    }

    @GetMapping("/{id}")
    public Mono<Order> getOrderById(@PathVariable("id") final String id) {
        logger.info("getOrder:  id = "+id);
        return this.orderService.getOrderById(id);
    }

    @PostMapping("")
    public Mono<Void> createOrder(@RequestBody final Mono<Order> order) {
        logger.info("createOrder: "+order.toString());
        return this.orderService.createOrUpdateOrder(order);
    }

    @DeleteMapping("/{id}")
    public Mono<Order> delete(@PathVariable("id") final String id) {
        return this.orderService.deleteOrder(id);
    }
}
