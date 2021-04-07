package com.yichen.nettydemo.propagationMechanism;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/1 10:15
 */
public class SampleOutBoundHandler extends ChannelOutboundHandlerAdapter {

    private final String name;
    public SampleOutBoundHandler(String name) {
        this.name = name;
    }
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("OutBoundHandler: " + name);
        super.write(ctx, msg, promise);
    }
}
