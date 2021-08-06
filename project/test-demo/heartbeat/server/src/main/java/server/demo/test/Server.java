package server.demo.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import server.demo.handler.RespHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/3 15:21
 * @describe
 */
//@Component
@Slf4j
public class Server implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        EventLoopGroup eventExecutors=new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class).remoteAddress("127.0.0.1",7421)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(180,180,0, TimeUnit.SECONDS))
                                    .addLast(new RespHandler());
                        }
                    });
            ChannelFuture future= bootstrap.connect();
            log.info("内网主机连接公网，端口为：7421");
            future.channel().closeFuture().sync();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            eventExecutors.shutdownGracefully();
        }

    }
}