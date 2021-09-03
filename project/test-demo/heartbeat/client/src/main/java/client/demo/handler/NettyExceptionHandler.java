package client.demo.handler;

import client.demo.model.VisitHost;
import client.demo.service.VisitHostService;
import client.demo.service.VisitLogService;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/9/3 9:12
 * @describe netty 的异常处理handler
 */
@Slf4j
public class NettyExceptionHandler extends ChannelDuplexHandler {
    
    @Autowired
    private VisitLogService visitLogService;
    
    @Autowired
    private VisitHostService visitHostService;
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 如果捕获到错误，则不在向后传播。
        if(cause instanceof DecoderException){
            log.error("NettyExceptionHandler 捕获传输解码错误，对方不知道我们自定义协议，拉黑该ip");
            ctx.disconnect();
            String remoteIp=ctx.channel().remoteAddress().toString();
            visitHostService.insert(remoteIp, "N", 0);
            List<VisitHost> visitHosts = visitHostService.getVisitHostByIp(remoteIp);
            if(visitHosts==null||visitHosts.size()!=0){
                log.error("NettyExceptionHandler =》 exceptionCaught =》 出现未知错误，查询记录条数不为一");
            }
            else{
                VisitHost visitHost=visitHosts.get(0);
                visitHost.setPreRejectTime(DateUtil.now());
                visitHost.setStatus("N");
                int a=visitHostService.update(visitHost);
                JSONObject extend=new JSONObject();
                extend.put("msg","netty 非法连接，对方使用的不是我们的协议");
                int b=visitLogService.insert(visitHost.getId(),"","N",extend);
                if(a>0&&b>0){
                    log.info("插入netty非法连接记录成功，visit_host id为 {}",visitHost.getId());
                }
                else{
                    log.error("插入netty非法连接记录失败，visit_host返回结果{},visit_log返回结果{},visit_host id为 {}",a,b,visitHost.getId());
                }
            }
        }
        else{
            log.error("NettyExceptionHandler 捕获异常，异常信息为{}",cause.getMessage());
        }
    }
}
