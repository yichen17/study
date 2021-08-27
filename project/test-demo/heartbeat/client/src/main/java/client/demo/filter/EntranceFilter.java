package client.demo.filter;

import client.demo.model.VisitHost;
import client.demo.service.VisitHostService;
import client.demo.utils.ReturnT;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
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
//@Order(0)
//@WebFilter(value = "/*")
//@Component
public class EntranceFilter implements Filter {

    private static Logger logger= LoggerFactory.getLogger(EntranceFilter.class);
    @Autowired
    private VisitHostService visitHostService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 包装 response 使得容易获取返回的响应数据
        ResponseWrapper wrapper = new ResponseWrapper((HttpServletResponse) response);
        HttpServletRequest req = (HttpServletRequest) request;
        logger.info("EntranceFilter开始执行过滤操作");
        String remoteAddr=request.getRemoteAddr();
        // TODO id自增必须为主键，而 此处 访问ip也必须是唯一的，如果将两个都设置为主键，达不到目标要求， 这里我使用了唯一索引通过捕获索引异常
        long res=visitHostService.insert(remoteAddr,"Y",0);
        if(res>0){
            logger.info("为新的访问ip，插入visit_host表，访问ip为{}",remoteAddr);
        }
        else{
            logger.info("访问ip已存在访问记录");
        }
        List<VisitHost> visitHosts = visitHostService.getVisitHostByIp(remoteAddr);
        if(visitHosts==null||visitHosts.size()!=1){
            logger.error("系统出现异常错误，访问ip:{}在visit_host表中存在多条记录",remoteAddr);
        }
        else{
            VisitHost visitHost=visitHosts.get(0);
            if(Objects.equals("Y",visitHost.getStatus())){
                logger.info("{}该ip地址被禁用",remoteAddr);
                // 定义统一的禁用返回信息
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.sendError(403,"请勿非法请求");
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
        // 请求的  uri
        String uri = req.getRequestURI();
        // 转换成 ReturnT
        ReturnT body = JSONObject.parseObject(result, ReturnT.class);



    }
}
