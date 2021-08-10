package com.yichen.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/6 11:42
 * @describe 解密
 */
public class RpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int code=in.readInt();
        int len=in.readInt();
        // 判断长度，如果为0 则表示为心跳
        NettyMessage nettyMessage=new NettyMessage();
        nettyMessage.setCode(code);
        if(len!=0){
            String data=in.readBytes(len).toString(CharsetUtil.UTF_8);
            nettyMessage.setData(data);
        }
        out.add(nettyMessage);
    }
}
