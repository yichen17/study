package com.yichen.nettydemo.decodeDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/1 13:48
 * @describe  特殊分隔符解码器   DelimiterBasedFrameDecoder
 */
public class DelimiterBasedServer {
    /**
     *  测试通过telnet     内容如下
     *  telnet localhost 8088 连接 服务端
     *     数据  hello&world&1234567890ab
     *  设置了报文长度为10，后面的长度超过10了，所以报异常。
     *
     */
    public static void main(String[] args) throws Exception {
        new DelimiterBasedServer().startEchoServer(8088);
    }

    public void startEchoServer(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ByteBuf delimiter = Unpooled.copiedBuffer("&".getBytes());
                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(10, true, true, delimiter));
                            ch.pipeline().addLast(new EchoServerHandler());
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
