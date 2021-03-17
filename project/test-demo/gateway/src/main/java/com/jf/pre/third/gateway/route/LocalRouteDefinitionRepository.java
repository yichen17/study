package com.jf.pre.third.gateway.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.synchronizedMap;

/**
 * @author liumg
 * @version 创建时间：2019/9/4.
 */
@Component
public class LocalRouteDefinitionRepository implements RouteDefinitionRepository {

    private static Logger logger = LoggerFactory.getLogger(LocalRouteDefinitionRepository.class );

    private final Map<String, RouteDefinition> routes = synchronizedMap(new LinkedHashMap<String, RouteDefinition>());

    public boolean exists(String routeId) {
        return routes.containsKey(routeId);
    }

    public Set<String> allRouteIds() {
        return routes.keySet();
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap( r -> {
            routes.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        routeId.subscribe(id -> {
            if (routes.containsKey(id)) {
                routes.remove(id);
            }
        });
        return Mono.empty();
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routes.values());
    }
}