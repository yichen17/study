package server.demo.handler;

import cn.hutool.http.HttpRequest;
import com.yichen.handler.NettyMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/5 16:25
 * @describe
 */
@Slf4j
public class RespHandler extends ChannelDuplexHandler {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 给外网调用发送反馈
        NettyMessage nettyMessage=(NettyMessage)msg;
        log.info("RespHandler接收到的msg: ,code= "+nettyMessage.getCode()+",data= "+nettyMessage.getData());
        if(2==nettyMessage.getCode()){
            //TODO 访问本地服务，获取结果  可进行优化
            String result="";
            try{
                // 调用方法时指定远程服务 的调用接口地址
                result= HttpRequest.post("localhost:8088"+nettyMessage.getData()).timeout(2000).execute().body();
                log.info("内部请求结果是"+result);
            }
            catch (Exception e){
                e.printStackTrace();
                result="RespHandler内部请求出错";
            }
            nettyMessage=new NettyMessage();
            nettyMessage.setCode(2);
            nettyMessage.setData(result);
            ctx.channel().writeAndFlush(nettyMessage).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        log.info("将结果发回给内网成功");
                    }
                    else{
                        log.info("发回数据给内网失败");
                        future.cause().printStackTrace();
                    }
                }
            });
        }
        else{
            log.info("其他code，具体内容为{}",msg);
            ctx.fireChannelRead(msg);
        }
    }






}
