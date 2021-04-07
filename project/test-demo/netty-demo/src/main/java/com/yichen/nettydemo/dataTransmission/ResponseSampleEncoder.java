package com.yichen.nettydemo.dataTransmission;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/4/1 14:26
 */
public class ResponseSampleEncoder extends MessageToByteEncoder<ResponseSample> {
      @Override
      protected void encode(ChannelHandlerContext ctx, ResponseSample msg, ByteBuf out) {
            if (msg != null) {
                  out.writeBytes(msg.getCode().getBytes());
                  out.writeBytes(msg.getData().getBytes());
                  out.writeLong(msg.getTimestamp());
            }
      }
}

