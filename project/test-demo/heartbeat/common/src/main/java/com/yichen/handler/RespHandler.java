package com.yichen.handler;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/5 16:25
 * @describe
 */
public class RespHandler extends ChannelDuplexHandler {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 给外网调用发送反馈
        System.out.println("RespHandler接收到的msg"+msg);
        NettyMessage nettyMessage=(NettyMessage)msg;
        if("2".equals(nettyMessage.getCode())){
            //TODO 访问本地服务，获取结果
            ctx.writeAndFlush(null);
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
                System.out.println("RespHandler当前状态为Read");
                //ctx.writeAndFlush(buildHeartBeat("1"));
            }
            if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("RespHandler当前状态为Write");
                //ctx.writeAndFlush(buildHeartBeat("0"));
            }
        }
    }

    private NettyMessage buildHeartBeat(String type) {
        NettyMessage msg = new NettyMessage();
        msg.setCode(type);
        return msg;
    }



}
