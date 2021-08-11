package server.demo.task;

import com.yichen.handler.RpcDecoder;
import com.yichen.handler.RpcEncoder;
import com.yichen.utils.TimeInterval;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import server.demo.handler.HeartBeatRespHandler;
import server.demo.handler.ReConnectionHandler;
import server.demo.handler.RespHandler;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/6 10:50
 * @describe
 */
@Component
@Slf4j
public class NettyTask implements Runnable, DisposableBean {

    @Value("${yichen.netty.server.port}")
    private int port;
    @Value("${yichen.netty.server.address}")
    private  String address;

    /**
     * 重新建立连接时间
     */
    private int retryInterval=1;

    private EventLoopGroup eventExecutors;
    private Bootstrap bootstrap;

    @Override
    public void run() {
        eventExecutors=new NioEventLoopGroup();
        ReConnectionHandler reConnectionHandler=new ReConnectionHandler(this);
        try{
            bootstrap=new Bootstrap();
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class).remoteAddress(address,port)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new RpcDecoder()).addLast(new RpcEncoder())
                                    .addLast(new IdleStateHandler(120,0,0, TimeUnit.SECONDS))
                                    .addLast(reConnectionHandler)
                                    .addLast(new HeartBeatRespHandler())
                                    .addLast(new RespHandler());
                        }
                    });
            reconnection(1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 建立连接，失败重连，最长间隔为1小时尝试一次
     * @param retryInternal 最开始的时间间隔
     * @throws Exception sleep中断
     */
    public void reconnection(int retryInternal)throws Exception{
        while(true){
            ChannelFuture future = connect();
            if(future==null){
                continue;
            }
            log.info("重连进行中...等待获取连接反馈future");
            while(!future.isDone()){

            }
            if(future.isSuccess()){
                log.info("连接成功");
                log.info("内网主机连接公网，ip地址为{},端口为：{}",address,port);
                break;
            }
            else{
                log.warn("连接失败，异常信息如下{}", Arrays.toString(future.cause().getStackTrace()));
            }
            log.info("指数退避，当前间隔时间{}",retryInternal);
            Thread.sleep(retryInternal);
            retryInternal= TimeInterval.getNextInternal(retryInternal,-1);
        }
    }

    /**
     * 连接服务端，也可用于失败重连
     */
    public ChannelFuture connect(){
        if(bootstrap!=null){
            return bootstrap.connect();
        }
        else{
            log.warn("内网bootstrap为null");
            return null;
        }
    }

    @Override
    public void destroy() throws Exception {
        if(eventExecutors!=null){
            eventExecutors.shutdownGracefully();
        }
    }
}
