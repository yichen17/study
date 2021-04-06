package com.yichen.nettydemo.analyse;
import com.yichen.nettydemo.dataTransmission.RequestSampleHandler;
import com.yichen.nettydemo.dataTransmission.ResponseSampleEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/6 14:02
 */
public class EchoServer {
    public static void main(String[] args)throws Exception {
        new EchoServer().startEchoServer(8088);
    }

    public void startEchoServer(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 设置ServerSocketChannel 对应的 Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 设置 SocketChannel 对应的 Handler
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(10));
                            ch.pipeline().addLast(new ResponseSampleEncoder());
                            ch.pipeline().addLast(new RequestSampleHandler());
                        }
                    });
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
