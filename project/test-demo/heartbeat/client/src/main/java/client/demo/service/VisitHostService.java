package client.demo.service;

import client.demo.dao.VisitHostMapper;
import client.demo.model.VisitHost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/26 15:48
 * @describe
 */

public interface VisitHostService {

    /**
     * 插入一条 访问记录，ip唯一，如果已存在对应ip，则插入失败(-1)
     * @param ip 访问的ip地址
     * @param status 是都被禁用 Y 被禁 N 没被禁
     * @param rejectTime 拒绝次数，到3次被禁
     * @return 插入成功后的id
     */
    public long insert(String ip,String status,int rejectTime);

    public VisitHost getVisitHostById(long id);

    public List<VisitHost> getVisitHostByIp(String ip);

    /**
     * 禁用 目标ip
     * @param ip 被禁用的ip地址
     * @return 影响行数
     */
    public int rejectIpByIp(String ip);

    /**
     * 通用更新方法
     * @param visitHost 要更新的字段
     * @return 影响行数
     */
    public int update(VisitHost visitHost);


}
