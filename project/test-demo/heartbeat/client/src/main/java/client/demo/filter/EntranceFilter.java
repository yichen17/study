package client.demo.filter;

import client.demo.model.VisitHost;
import client.demo.model.VisitLog;
import client.demo.service.VisitHostService;
import client.demo.service.VisitLogService;
import client.demo.utils.ReturnT;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/26 10:55
 * @describe 入口过滤器
 *  通过一下三个注解这里实现过滤器功能，也可以通过 FilterConfig 使 filter 生效
 */
@Order(0)
@WebFilter(value = "/*")
@Component
public class EntranceFilter implements Filter {

    private static Logger logger= LoggerFactory.getLogger(EntranceFilter.class);
    @Autowired
    private VisitHostService visitHostService;

    @Autowired
    private VisitLogService visitLogService;

    @Value("${yichen.reject.times}")
    private Integer rejectTime;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 包装 response 使得容易获取返回的响应数据
        ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);
        HttpServletRequest req = (HttpServletRequest) request;
        // 请求的  uri
        String uri = req.getRequestURI();
        long hostId=-1;
        int localRejectTime=-1;
        logger.info("EntranceFilter开始执行过滤操作");
        String remoteAddr=request.getRemoteAddr();
        // TODO id自增必须为主键，而 此处 访问ip也必须是唯一的，如果将两个都设置为主键，达不到目标要求， 这里我使用了唯一索引通过捕获索引异常
        long res=visitHostService.insert(remoteAddr,"Y",rejectTime);
        if(res>0){
            logger.info("为新的访问ip，插入visit_host表，访问ip为{}",remoteAddr);
        }
        else{
            logger.info("访问ip已存在访问记录");
        }
        List<VisitHost> visitHosts = visitHostService.getVisitHostByIp(remoteAddr);
        if(visitHosts==null||visitHosts.size()!=1){
            logger.error("系统出现异常错误，访问ip:{}在visit_host表中存在多条记录",remoteAddr);
            visitLogService.insert(hostId,uri,"E");
            logger.info("系统异常日志已记录");
            return ;
        }
        else{
            VisitHost visitHost=visitHosts.get(0);
            localRejectTime=visitHost.getRejectTimes();
            hostId=visitHost.getId();
            if(Objects.equals("N",visitHost.getStatus())){
                logger.info("{}该ip地址被禁用",remoteAddr);
                // 定义统一的禁用返回信息
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.getOutputStream().write("Please do not visit casually. You have been blacklisted".getBytes());
                httpServletResponse.flushBuffer();
                visitLogService.insert(hostId,uri,"L");
                logger.info("拦截日志已记录");
                return ;
            }
            else{
                // 放行。让请求继续执行
                chain.doFilter(request,wrapper);
            }
        }

        // 对 请求结果做判断
        String result = wrapper.getResponseData(response.getCharacterEncoding());
        response.getOutputStream().write(result.getBytes());

        // 转换成 ReturnT
        ReturnT body = JSONObject.parseObject(result, ReturnT.class);
        logger.info("请求url:{} => 请求返回的状态码  {}",uri,body.getCode());
        // 查询历史错误数据
        List<VisitLog> visitLogByHostIdAndValid = visitLogService.getVisitLogByHostIdAndValid(hostId);
        // 逻辑，如果是常规请求，则将前面的异常请求重置，具体数值可以自定义，如果为异常信息则直接记录
        if(Objects.equals("2",body.getCode())){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("msg",body.getMsg());
            visitLogService.insert(hostId,uri,"N",jsonObject);
            logger.info("异常请求日志已记录");
            // 拒绝次数达到指定次数，ip禁用
            if(visitLogByHostIdAndValid.size()>=localRejectTime){
                int d = visitHostService.rejectIpByIp(remoteAddr);
                if(d>0){
                    logger.info("ip:{}被禁止访问",remoteAddr);
                }
            }
        }
        else{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("data",body.getData());
            jsonObject.put("msg",body.getMsg());
            visitLogService.insert(hostId,uri,"Y",jsonObject);
            logger.info("正常请求日志已记录");
            int r = visitLogService.invalidLogByHostId(hostId);
            logger.info("成功将{}条记录设置为无效记录",r);
        }
        


    }
}