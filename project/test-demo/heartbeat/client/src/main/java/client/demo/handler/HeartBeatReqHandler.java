package client.demo.handler;

import com.yichen.handler.NettyMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import static com.yichen.handler.NettyMessage.buildHeartBeat;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/10 10:07
 * @describe 心跳发送 handler
 */
@Slf4j
public class HeartBeatReqHandler extends ChannelDuplexHandler {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("断开连接");
                ctx.disconnect();
            }
            if (event.state() == IdleState.WRITER_IDLE) {
                log.info("发送心跳");
                ChannelFuture future = ctx.writeAndFlush(buildHeartBeat(0));
                future.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if(future.isSuccess()){
                            System.out.println("发送心跳成功");
                        }
                        else{
                            future.cause().printStackTrace();
                        }
                    }
                });
            }
        }
    }



}
