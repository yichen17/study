package com.jf.crawl.cloud.callback.dao;

import com.jf.crawl.cloud.callback.entity.CallbackConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description 
 * @author Eric Lau
 * @time 下午8:00:52
*/
@Mapper
public interface CallbackConfigDao {

	@Select("select * from t_callback_config where app_id = #{app_id} and product = #{product} and notify_event = #{notify_event} " +
			"UNION select * from t_callback_config where app_id = \'COMMON\' and product = \'COMMON\' and notify_event = \'task.success\' " +
			"and NOT EXISTS (select * from t_callback_config where app_id =#{app_id} and product = #{product} and notify_event = #{notify_event} )")
	List<CallbackConfigEntity> selectConfigByAppIdAndNotifyEvent(@Param("app_id") String appId,
                                                             @Param("product") String product,
                                                             @Param("notify_event") String notifyEvent);
}
