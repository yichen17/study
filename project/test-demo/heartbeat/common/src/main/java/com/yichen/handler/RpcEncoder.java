package com.yichen.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/6 11:41
 * @describe 加密
 */
public class RpcEncoder extends MessageToByteEncoder<NettyMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getCode());
        // 判空逻辑 如果是心跳包需要判空，不然存在异常
        String data=msg.getData();
        if(data==null||"".equals(data)){
            out.writeInt(0);
        }
        else{
            byte [] datas=msg.getData().getBytes(CharsetUtil.UTF_8);
            out.writeInt(datas.length);
            out.writeBytes(datas);
        }
    }
}
