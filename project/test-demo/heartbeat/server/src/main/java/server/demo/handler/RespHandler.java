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
            //TODO 访问本地服务，获取结果
            String result="";
            try{
                result= HttpRequest.post("localhost:8088/get").timeout(2000).execute().body();
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
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("RespHandler当前状态为Read");
                //ctx.writeAndFlush(buildHeartBeat("1"));
            }
            if (event.state() == IdleState.WRITER_IDLE) {
                log.info("RespHandler当前状态为Write");
                //ctx.writeAndFlush(buildHeartBeat("0"));
            }
        }
    }

    private NettyMessage buildHeartBeat(int type) {
        NettyMessage msg = new NettyMessage();
        msg.setCode(type);
        return msg;
    }



}
