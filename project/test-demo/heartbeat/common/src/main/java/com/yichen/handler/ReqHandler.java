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
public class ReqHandler extends ChannelDuplexHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 解析内网服务反馈的结果，发送给 controller
        super.channelRead(ctx, msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {


        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("ReqHandler当前状态为Read");
                // 读状态理论应该将结果返回 controller  TODO 理解错误 这里evt是通知，表示指定时间没有接收数据
                //ctx.writeAndFlush(buildHeartBeat("0"));
            }
            if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("ReqHandler当前状态为Write");
                // 写状态为请求转发，将 controller 的请求转发给内网机器 TODO 理解错误  表示指定时间没有发送数据
                ctx.writeAndFlush(buildHeartBeat("0"));
            }
        }
    }

    private NettyMessage buildHeartBeat(String type) {
        NettyMessage msg = new NettyMessage();
        msg.setCode(type);
        return msg;
    }
}
