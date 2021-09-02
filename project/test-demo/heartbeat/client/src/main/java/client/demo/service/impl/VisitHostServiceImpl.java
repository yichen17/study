package client.demo.service.impl;

import client.demo.dao.VisitHostMapper;
import client.demo.model.VisitHost;
import client.demo.model.VisitHostExample;
import client.demo.service.VisitHostService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/26 15:49
 * @describe
 */
@Service
@Slf4j
public class VisitHostServiceImpl implements VisitHostService {

    @Autowired
    private VisitHostMapper visitHostMapper;


    @Override
    public long insert(String ip,String status,int rejectTime) {
        log.info("VisitHostServiceImpl =》 insert方法入参 =》ip:{},status:{},rejectTime:{}",ip,status,rejectTime);
        VisitHost visitHost = new VisitHost();
        visitHost.setIp(ip);
        visitHost.setStatus(status);
        visitHost.setRejectTimes(rejectTime);

        int res = 0;
        try{
            res = visitHostMapper.insert(visitHost);
            log.info("查询结果为{}",res);
            if(res>0){
                return visitHost.getId();
            }
            else{
                return res;
            }
        }
        catch (Exception e){
            log.error("插入visit_host表出错，内容为{}",e.getMessage());
            return -1;
        }

    }

    @Override
    public VisitHost getVisitHostById(long id) {
        log.info("VisitHostServiceImpl =》 getVisitHostById方法入参 =》id:{}",id);
        return visitHostMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<VisitHost> getVisitHostByIp(String ip) {
        log.info("VisitHostServiceImpl =》 getVisitHostByIp方法入参 =》ip:{}",ip);
        VisitHostExample example=new VisitHostExample();
        example.createCriteria().andIpEqualTo(ip);
        return visitHostMapper.selectByExample(example);
    }

    @Override
    public int rejectIpByIp(String ip) {
        log.info("VisitHostServiceImpl =》 rejectIpByIp =》ip:{}",ip);
        VisitHost host = getVisitHostByIp(ip).get(0);
        VisitHostExample example=new VisitHostExample();
        example.createCriteria().andIpEqualTo(ip);
        host.setStatus("N");
        //  前面为更新的值，后面为条件
        return visitHostMapper.updateByExample(host,example);
    }

    @Override
    public int update(VisitHost visitHost) {
        log.info("VisitHostServiceImpl =》 rejectIpByIp 入参{}", JSONObject.toJSONString(visitHost));
        VisitHostExample example=new VisitHostExample();
        example.createCriteria().andIdEqualTo(visitHost.getId());
        return visitHostMapper.updateByExample(visitHost,example);
    }
}
