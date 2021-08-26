package client.demo.filter;

import client.demo.dao.VisitHostMapper;
import client.demo.dao.VisitLogMapper;
import client.demo.model.VisitHost;
import client.demo.model.VisitHostExample;
import client.demo.model.VisitLog;
import client.demo.service.VisitHostService;
import client.demo.service.VisitLogService;
import com.sun.xml.internal.bind.v2.TODO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/26 10:55
 * @describe 入口过滤器
 *  通过一下三个注解这里实现过滤器功能，也可以通过 FilterConfig 使 filter 生效
 */
//@Order(0)
//@WebFilter(value = "/*")
//@Component
public class EntranceFilter implements Filter {

    private static Logger logger= LoggerFactory.getLogger(EntranceFilter.class);
    @Autowired
    private VisitHostService visitHostService;
    @Autowired
    private VisitLogService visitLogService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // TODO 分析调用链，感觉和 netty 的channel 类似
        logger.info("EntranceFilter开始执行过滤操作");
        String remoteAddr=request.getRemoteAddr();
        // TODO id自增必须为主键，而 此处 访问ip也必须是唯一的，如果将两个都设置为主键，达不到目标要求， 这里我使用了唯一索引通过捕获索引异常
        long res=visitHostService.insert(remoteAddr,"Y",0);
        if(res>0){
            logger.info("为新的访问ip，插入visit_host表，访问ip为{}",remoteAddr);
        }
        else{
            logger.info("访问ip已存在访问记录");
            res=visitHostService.getVisitHostByIp(remoteAddr).get(0).getId();
        }

        String url=((HttpServletRequest)request).getRequestURI();
        res=visitLogService.insert(res,url,"Y");
        if(res>0){
            logger.info("visitLog表插入成功");
        }
        else{
            logger.info("visitLog表插入失败");
        }
        chain.doFilter(request,response);
    }
}
