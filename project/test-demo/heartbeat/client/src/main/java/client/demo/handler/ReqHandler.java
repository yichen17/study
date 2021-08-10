package client.demo.handler;

import client.demo.utils.MapTools;
import com.yichen.handler.NettyMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/5 16:25
 * @describe
 */
@Slf4j
public class ReqHandler extends ChannelDuplexHandler {

    /**
     * 失败重试次数，采用二进制退避算法 读
     */
    private static Integer readRetryTime=0;
    /**
     * 失败重试次数，采用二进制退避算法 写
     */
    private static Integer writeRetryTime=0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            NettyMessage nettyMessage=(NettyMessage)msg;
            log.info("ReqHandler读取到的数据是: code= "+nettyMessage.getCode()+",data= "+nettyMessage.getData());
            if(2==nettyMessage.getCode()){
                // 解析内网服务反馈的结果，放入 future中，供 controller 获取
                CompletableFuture<String> future=(CompletableFuture<String>)MapTools.channelFutureMap.get(ctx.channel());
                future.complete(nettyMessage.getData());
            }
            else{
                ctx.fireChannelRead(msg);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketAddress address = ctx.channel().remoteAddress();
        log.info("当前管道激活，管道是{}",ctx.channel());
        log.info("新接入管道对应的客户端ip地址为{}",address);
        MapTools.ips.add(address.toString());
        MapTools.channels.add(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("当前管道关闭，管道是"+ctx.channel()+"移除对应ip信息"+ctx.channel().remoteAddress().toString());
        MapTools.ips.remove(ctx.channel().remoteAddress().toString());
        MapTools.channels.remove(ctx.channel());
        super.channelInactive(ctx);
    }
}
