package client.demo.service.impl;

import client.demo.dao.VisitLogMapper;
import client.demo.model.VisitHostExample;
import client.demo.model.VisitLog;
import client.demo.model.VisitLogExample;
import client.demo.service.VisitLogService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/26 15:54
 * @describe visit_log 实现类
 */
@Service
@Slf4j
public class VisitLogServiceImpl implements VisitLogService {


    @Autowired
    private VisitLogMapper visitLogMapper;

    @Override
    public int insert(long hostId, String url, String status) {
        log.info("VisitLogServiceImpl =》insert方法入参 =》 hostId:{},url:{},status:{}",hostId,url,status);
        VisitLog log=new VisitLog();
        log.setHostId(hostId);
        log.setVisitUrl(url);
        log.setStatus(status);
        return visitLogMapper.insert(log);
    }

    @Override
    public int insert(long hostId, String url, String status, JSONObject msg) {
        log.info("VisitLogServiceImpl =》insert方法入参 =》 hostId:{},url:{},status:{},extend:{}",hostId,url,status,msg);
        VisitLog log=new VisitLog();
        log.setHostId(hostId);
        log.setVisitUrl(url);
        log.setStatus(status);
        log.setExtend(msg);
        return visitLogMapper.insertExtend(log);
    }

    @Override
    public List<VisitLog> getVisitLogByHostIdAndValid(long hostId) {
        log.info("VisitLogServiceImpl =》getVisitLogByHostIdAndValid 方法入参 =》 hostId:{}",hostId);
        VisitLogExample example=new VisitLogExample();
        example.createCriteria().andHostIdEqualTo(hostId)
                .andStatusEqualTo("N").andValidEqualTo("Y");
        return visitLogMapper.selectByExample(example);
    }

    @Override
    public int invalidLogByHostId(long hostId) {
        log.info("VisitLogServiceImpl =》invalidLogByHostId 方法入参 =》 hostId:{}",hostId);
        int res=0;
        List<VisitLog> logs = getVisitLogByHostIdAndValid(hostId);
        for(VisitLog log:logs){
            log.setValid("N");
            VisitLogExample example=new VisitLogExample();
            example.createCriteria().andHostIdEqualTo(hostId).andValidEqualTo("Y").andStatusEqualTo("N");

            res+=visitLogMapper.updateByExample(log,example);
        }
        return  res;
    }

}
