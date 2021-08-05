package com.yichen.handler;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/5 16:32
 * @describe netty 自定义消息实体
 */
@Data
public class NettyMessage<T> implements Serializable {
    /**
     * 0 表示写  1 表示读   2表示通信
     */
    private String code;

    private T data;
}
