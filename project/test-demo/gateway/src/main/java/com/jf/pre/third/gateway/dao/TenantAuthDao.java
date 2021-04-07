package com.jf.pre.third.gateway.dao;

import com.jf.pre.third.gateway.entity.GatewayAuthEntity;
import com.jf.pre.third.gateway.entity.GatewayWhiteIpEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 鉴权相关dao
 * @author liumg
 * @version 创建时间：2019/6/27.
 */
@Mapper
public interface TenantAuthDao {

    /**
     * 根据appId 判断是否为正常商户
     * @param appId 商户唯一号
     * @param product 业务类型
     * @param token 与商户Id成对出现
     * @return 0：标识无， 1：标识正常
     */
    int hasValidTenantByAppIdAndToken(@Param("appId") String appId,
                                      @Param("token") String token,
                                      @Param("product") String product);

    /**
     * 根据appid获取ip的正则表达式
     * @param appId 商户唯一id
     * @param product 业务类型
     * @param ip 服务IP
     * @return 返回ip的正则表达式
     */
    int selectNetworkPermissionRegexByAppId(@Param("appId") String appId,
                                               @Param("product") String product,
                                                @Param("ip") String ip);

    /**
     * 获取所有的合法商户信息
     * @return 合法商户实体类
     */
    List<GatewayAuthEntity> selectAllValidTenant();

    /**
     * 获取商户白名单
     * @return 商户白名单实体类列表
     */
    List<GatewayWhiteIpEntity> selectAllTenantWhiteIps();

}
