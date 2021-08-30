package client.demo.service;

import client.demo.model.VisitLog;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/26 15:52
 * @describe visit_log 接口
 */
public interface VisitLogService {
    /**
     * 插入 请求日志
     * @param hostId visit_host 表的id值
     * @param url 请求路径 url
     * @param status 请求结果
     * @return 返回影响行数
     */
    public int insert(long hostId,String url,String status);

    /**
     * 插入请求日志附带记录额外信息
     * @param hostId visit_host 表的 id值
     * @param url 请求路径 url
     * @param status 请求结果
     * @param msg 额外信息
     * @return 返回影响行数
     */
    public int insert(long hostId, String url, String status, JSONObject msg);

    /**
     * 根据 hostId查询有效的 访问记录
     * @param hostId visit_host 对应的id
     * @return  已该 id 查询 visit_log 符合的表
     */
    public List<VisitLog> getVisitLogByHostIdAndValid(long hostId);

    /**
     * 将对应的 hostId 相关记录设置为无效
     * @param hostId 目标 hostId
     * @return 影响行数
     */
    public int invalidLogByHostId(long hostId);

}
