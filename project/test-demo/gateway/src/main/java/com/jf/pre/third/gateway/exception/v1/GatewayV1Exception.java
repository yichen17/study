package com.jf.pre.third.gateway.exception.v1;

/**
 * @author liumg
 * @version 创建时间：2019/7/17.
 */
public class GatewayV1Exception extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    public GatewayV1Exception(GatewayV1ExceptionCode exceptionCode) {
        super();
        this.code = exceptionCode.getValue();
        this.msg = exceptionCode.getDesc();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
