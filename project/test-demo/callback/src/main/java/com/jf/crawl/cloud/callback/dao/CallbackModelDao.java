package com.jf.crawl.cloud.callback.dao;

import com.jf.crawl.cloud.callback.entity.CallbackModelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 业务回调模型
 * @author Eric Liu
 */
@Mapper
public interface CallbackModelDao {

	/**
	 * 通过商户号，业务类型，事件获取回调模型
	 *
     * @param id
     * @param appId 商户唯一id
     * @param product 业务类型，CRED,INSU等
     * @param event 事件类型，task.success等
     * @return 回调模型
	 */
	CallbackModelEntity selectModelByAppIdAndProduct(@Param("app_id") String appId,
                                                     @Param("product") String product,
                                                     @Param("event") String event,@Param("partner") String partner);
	
}
