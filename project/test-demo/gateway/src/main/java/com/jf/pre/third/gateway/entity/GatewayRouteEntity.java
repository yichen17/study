package com.jf.pre.third.gateway.entity;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.util.DigestUtils;

import java.net.URI;
import java.sql.Date;
import java.util.List;

/**
 * @author liumg
 * @version 创建时间：2019/7/10.
 */
public class GatewayRouteEntity {

    /**
     * 自增id
     */
    String id;

    /**
     * 路由唯一id
     */
    int order;

    /**
     * 路由filters信息
     */
    String filters;

    /**
     * 请求地址uri路径
     */
    String uri;

    /**
     * 路由predicates信息
     */
    String predicates;

    /**
     * 路由状态：0标识已启用，-1：初始值，1：标识禁用
     */
    int status;

    /**
     * 路由创建时间
     */
    Date createTime;

    /**
     * 路由更新时间
     */
    Date updateTime;

    RouteDefinition routeDefinition;

    public GatewayRouteEntity () {
        routeDefinition = new RouteDefinition();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.routeDefinition.setId(id);
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
        this.routeDefinition.setOrder(order);
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;

        if (!StringUtils.isEmpty(filters)) {
            List<FilterDefinition> filterDefinitionList =
                    JSON.parseArray(filters, FilterDefinition.class);
            this.routeDefinition.setFilters(filterDefinitionList);
        }
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {

        this.uri = uri;
        try {
            this.routeDefinition.setUri(new URI(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPredicates() {
        return predicates;
    }

    public void setPredicates(String predicates) {
        this.predicates = predicates;

        if (!StringUtils.isEmpty(predicates)) {
            List<PredicateDefinition> predicateDefinitionList =
                    JSON.parseArray(predicates, PredicateDefinition.class);
            this.routeDefinition.setPredicates(predicateDefinitionList);
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public RouteDefinition getRouteDefinition() {
        return routeDefinition;
    }

    public void setRouteDefinition(RouteDefinition routeDefinition) {
        this.routeDefinition = routeDefinition;
    }

    public String getMd5() {
        return DigestUtils.md5DigestAsHex(JSON.toJSONBytes(this.routeDefinition));
    }

    @Override
    public String toString() {
        return "GatewayRouteEntity{" +
                "id='" + id + '\'' +
                ", order=" + order +
                ", filters='" + filters + '\'' +
                ", uri='" + uri + '\'' +
                ", predicates='" + predicates + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", routeDefinition=" + routeDefinition +
                '}';
    }
}
