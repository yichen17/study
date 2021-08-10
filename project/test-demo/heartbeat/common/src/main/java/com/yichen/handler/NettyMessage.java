package com.yichen.handler;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/5 16:32
 * @describe netty 自定义消息实体
 */
@Data
@ToString
public class NettyMessage implements Serializable {
    /**
     * 0 中台发心跳包  1 内部服务器反馈心跳包   2表示通信
     */
    private int code;

    private String data;

    /**
     * 构建心跳
     * @param type 心跳类型
     * @return 心跳消息
     */
    public static NettyMessage buildHeartBeat(int type) {
        NettyMessage msg = new NettyMessage();
        msg.setCode(type);
        return msg;
    }

}
