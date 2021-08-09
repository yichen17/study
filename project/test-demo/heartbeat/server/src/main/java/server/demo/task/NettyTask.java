package server.demo.task;

import com.yichen.handler.RpcDecoder;
import com.yichen.handler.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import server.demo.handler.RespHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/6 10:50
 * @describe
 */
@Component
@Slf4j
public class NettyTask implements Runnable{

    private static final String ALIYUN="152.136.237.34";

    private static final String LOCAL="127.0.0.1";

    @Override
    public void run() {
        EventLoopGroup eventExecutors=new NioEventLoopGroup();
        try{
            Bootstrap bootstrap=new Bootstrap();
            bootstrap.group(eventExecutors).channel(NioSocketChannel.class).remoteAddress(LOCAL,7421)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new RpcDecoder()).addLast(new RpcEncoder())
                                    .addLast(new IdleStateHandler(20,5,0, TimeUnit.SECONDS))
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
