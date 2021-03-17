package com.jf.pre.third.gateway.dao;

import com.jf.pre.third.gateway.entity.GatewayRouteEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 网关路由dao
 * @author liumg
 * @version 创建时间：2019/7/9.
 */
@Mapper
public interface GatewayRouteDao {

    /**
     * 获取所有可用路由信息
     * @return 路由信息列表
     */
   List<GatewayRouteEntity> selectAllGatewayRoute();
}
