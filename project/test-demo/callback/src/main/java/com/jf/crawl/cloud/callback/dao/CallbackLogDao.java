package com.jf.crawl.cloud.callback.dao;

import com.jf.crawl.cloud.callback.entity.CallbackLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 
 * @author Eric Lau
 * @time 上午10:43:04
*/
@Mapper
public interface CallbackLogDao {

    /**
     * 插入回调日志
     * @param record
     * @return
     */
    int insertCallbackLog(CallbackLogEntity record);

    /**
     * 通过任务号，流水号更新回调事件日志
     * @param record
     * @return
     */
    int updateCallbackEventLogByTaskKeyAndTransNo(CallbackLogEntity record);
}
