package client.demo.filter;

import client.demo.model.VisitHost;
import client.demo.model.VisitLog;
import client.demo.service.VisitHostService;
import client.demo.service.VisitLogService;
import client.demo.utils.ReturnT;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.connector.Response;
import org.apache.catalina.connector.ResponseFacade;
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
import java.lang.reflect.Field;
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
@WebFilter(urlPatterns = "/*")
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
        HttpServletRequest req = (HttpServletRequest) request;
        // 请求的  uri
        String uri = req.getRequestURI();
        // 排除与前端交互的uri，它们返回的是 字符串，没有走我们默认的 返回消息体。
        if(uri.startsWith("/user")||uri.startsWith("/video")){
            logger.info("请求uri为{},不进行请求记录",uri);
            chain.doFilter(request,response);
            return ;
        }

        // 包装 response 使得容易获取返回的响应数据
        ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);
        VisitHost visitHost=null;
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
            visitHost=visitHosts.get(0);
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
                logger.info("请求uri{}",uri);
                // 放行。让请求继续执行
                chain.doFilter(request,wrapper);
            }
        }

        // 对 请求结果做判断
        String result = wrapper.getResponseData(response.getCharacterEncoding());
        logger.info("获取的返回结果为{}",result);
        if(result==null||"".equals(result)){
            // 排除正常请求时无返回数据的情况，例如  请求重定向
            if(wrapper.getStatus()==302){
                // TODO shiro 登陆拦截后应该调回原请求地址
                // 重定向不算一次成功请求，只算部分成功
                logger.info("为 302 临时重定向，重定向地址为{},原请求地址为{}",wrapper.getHeader("Location"),uri);
                return ;
            }
            // 请求结果为空说明调用链存在异常，一种情况是 dispatch 无法分发请求路由
            result=JSONObject.toJSONString(new ReturnT("2","请核对你的请求，请勿随意访问"));
            logger.warn("请求调用返回结果为空，设定默认值");
            try{
                Class clazz=ResponseFacade.class;
                Field field=clazz.getDeclaredField("response");
                field.setAccessible(true);
                Object o = field.get(((ResponseFacade) response));
                //  修改状态，默认情况 放过调用链执行后 response.setStatus 失败
                ((Response)o).setAppCommitted(false);
                ((Response)o).setSuspended(false);
            }
            catch (Exception e){
                logger.error("========> 反射获取response失败");
            }

        }
        // =====>  reset() 方法可以清空缓冲区以及重置状态码 200   但是这里又需要依赖前面的try模块，需要先设置他们
        //  =====>  还需要设置 内容类型，这块也被重置了
        response.reset();
        response.setContentType("text/html;charset=UTF-8");
        response.getOutputStream().write(result.getBytes());
        wrapper.flushBuffer();
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
            if(visitLogByHostIdAndValid.size()>localRejectTime){
                int d = visitHostService.rejectIpByIp(remoteAddr);
                if(d>0){
                    logger.info("ip:{}被禁止访问",remoteAddr);
                }
            }
            // 如果有异常请求日志，则更新 visit_host 表的 pre_reject_time字段
            visitHost.setPreRejectTime(DateUtil.now());
            int d=visitHostService.update(visitHost);
            if(d==1){
                logger.info("visit_host表更新对应的拒绝时间成功");
            }
            else{
                logger.error("visit_host更新拒绝时间失败，参数为{},执行影响行数为{}",visitHost,d);
            }


        }
        else{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("data",body.getData());
            jsonObject.put("msg",body.getMsg());
            visitLogService.insert(hostId,uri,"Y",jsonObject);
            logger.info("正常请求日志已记录");
            int d = visitLogService.invalidLogByHostId(hostId);
            logger.info("成功将{}条记录设置为无效记录",d);
        }
        


    }
}
