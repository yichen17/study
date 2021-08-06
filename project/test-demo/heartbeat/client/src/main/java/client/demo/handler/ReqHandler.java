package client.demo.handler;

import client.demo.utils.MapTools;
import com.yichen.handler.NettyMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/5 16:25
 * @describe
 */
@Slf4j
public class ReqHandler extends ChannelDuplexHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // TODO 解析内网服务反馈的结果，发送给 controller
        try{
            NettyMessage nettyMessage=(NettyMessage)msg;
            log.info("ReqHandler读取到的数据是: code= "+nettyMessage.getCode()+",data= "+nettyMessage.getData());
            CompletableFuture<String> future=(CompletableFuture<String>)MapTools.channelFutureMap.get(ctx.channel());
            future.complete(nettyMessage.getData());
            //super.channelRead(ctx, msg);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("当前管道激活，管道是{}",ctx.channel());
        MapTools.channels.add(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("当前管道激活，管道是"+ctx.channel());
        MapTools.channels.remove(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {


        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("ReqHandler当前状态为Read");
                // 读状态理论应该将结果返回 controller  TODO 理解错误 这里evt是通知，表示指定时间没有接收数据
                //ctx.writeAndFlush(buildHeartBeat(0));
            }
            if (event.state() == IdleState.WRITER_IDLE) {
                log.info("ReqHandler当前状态为Write");
                // 写状态为请求转发，将 controller 的请求转发给内网机器 TODO 理解错误  表示指定时间没有发送数据
                ctx.writeAndFlush(buildHeartBeat(0));
            }
        }
    }

    private NettyMessage buildHeartBeat(int type) {
        NettyMessage msg = new NettyMessage();
        msg.setCode(type);
        return msg;
    }
}
