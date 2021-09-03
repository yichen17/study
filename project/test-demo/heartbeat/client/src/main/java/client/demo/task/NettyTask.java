package client.demo.task;

import client.demo.handler.HeartBeatReqHandler;
import client.demo.handler.NettyExceptionHandler;
import client.demo.handler.ReqHandler;
import com.yichen.handler.RpcDecoder;
import com.yichen.handler.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/6 10:47
 * @describe
 */
@Component
@Slf4j
public class NettyTask implements Runnable{

    @Value("${yichen.netty.server.port}")
    private int port;

    @Override
    public void run() {
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup=new NioEventLoopGroup();
        try{
            ServerBootstrap b=new ServerBootstrap();
            b.group(bossGroup,workGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new RpcDecoder()).addLast(new RpcEncoder())
                                    // 指定心跳频率  60s发送一次心跳
                                    .addLast(new IdleStateHandler(120,90,0, TimeUnit.SECONDS))
                                    .addLast(new HeartBeatReqHandler())
                                    .addLast(new ReqHandler()).addLast(new NettyExceptionHandler());
                        }
                    });
            ChannelFuture f=b.bind(7421).sync();
            log.info("公网 netty 启动，暴露端口为:{}",port);
            f.channel().closeFuture().sync();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
