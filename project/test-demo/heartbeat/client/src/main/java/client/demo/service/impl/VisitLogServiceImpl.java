package client.demo.service.impl;

import client.demo.dao.VisitLogMapper;
import client.demo.model.VisitLog;
import client.demo.service.VisitLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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
}
