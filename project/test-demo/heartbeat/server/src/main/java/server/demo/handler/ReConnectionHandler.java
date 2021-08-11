package server.demo.handler;

import com.yichen.utils.TimeInterval;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import server.demo.task.NettyTask;

import java.util.Arrays;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/11 9:37
 * @describe 失败重连 handler
 */
@Slf4j
@ChannelHandler.Sharable
public class ReConnectionHandler extends ChannelInboundHandlerAdapter {


    private NettyTask task;

    public ReConnectionHandler(NettyTask task){
        this.task=task;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("重连handler管道激活");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("重连handler管道失效,开启指数退避重连");
        task.reconnection(1);
    }
}
