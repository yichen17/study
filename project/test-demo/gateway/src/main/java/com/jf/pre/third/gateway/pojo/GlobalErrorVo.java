package com.jf.pre.third.gateway.pojo;

import java.util.ArrayList;

/**
 * 全局错误统一返回处理
 * @author liumg
 * @version 创建时间：2019/7/9.
 */
public class GlobalErrorVo {

    /**
     * 相应码， 成功返回200， 失败详见具体的readme
     */
    private int code;

    /**
     * 响应码对应描述信息
     */
    private String message;

    /**
     * 请求发生异常时，对应的path路径
     */
    private String path;

    /**
     * 异常方式的对应的时间戳
     */
    private long timestamp;

    /**
     * 错误码
     */
    private String error;

    /**
     * 错误码的详细描述
     */
    private ArrayList<String> errors;

    /**
     * 流水号
     */
    private String requestId;

    public GlobalErrorVo() {
        errors = new ArrayList<>(4);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<String> errors) {
        this.errors = errors;
    }

    public void setErrors(String message) {
        this.errors.add(message);
    }
}
