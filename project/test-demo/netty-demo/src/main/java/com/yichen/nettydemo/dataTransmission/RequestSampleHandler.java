package com.yichen.nettydemo.dataTransmission;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/1 14:26
 */
public class RequestSampleHandler extends ChannelInboundHandlerAdapter {
      @Override
      public void channelRead(ChannelHandlerContext ctx, Object msg) {
            String data = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
            ResponseSample response = new ResponseSample("OK", data, System.currentTimeMillis());
            ctx.channel().writeAndFlush(response);
      }
}

