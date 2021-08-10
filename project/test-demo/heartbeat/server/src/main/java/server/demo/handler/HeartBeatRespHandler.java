package server.demo.handler;

import com.yichen.handler.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import static com.yichen.handler.NettyMessage.buildHeartBeat;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/10 9:56
 * @describe 心跳反馈handler
 */
@Slf4j
public class HeartBeatRespHandler extends SimpleChannelInboundHandler<NettyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        NettyMessage nettyMessage=(NettyMessage)msg;
        log.info("RespHandler接收到的msg: ,code= "+nettyMessage.getCode()+",data= "+nettyMessage.getData());
        if(0==nettyMessage.getCode()){
            log.info(">>> 接收到心跳");
            ctx.writeAndFlush(buildHeartBeat(1));
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
                log.info("没有接收到心跳，断开连接");
                ctx.disconnect();
            }
        }
    }

}
