package com.jf.pre.third.gateway.exception;

/**
 * @author liumg
 * @version 创建时间：2019/7/17.
 */
public class GatewayException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private GatewayExceptionCode code;

    public GatewayException(GatewayExceptionCode code) {
        super();
        this.code = code;
    }

    public GatewayExceptionCode getCode() {
        return code;
    }

    public void setCode(GatewayExceptionCode code) {
        this.code = code;
    }

}
