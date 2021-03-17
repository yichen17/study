package com.jf.pre.third.gateway.pojo.v1;

/**
 * @author liumg
 * @version 创建时间：2019/7/18.
 */
public class GlobalErrorVoV1 {

    /**
     * 错误码
     */
    String errorCode;

    /**
     * 调用花费的时间
     */
    String spendTime;

    /**
     * 接口状态
     */
    String status;

    /**
     * 流水号
     */
    String transNo;

    /**
     * 调用开始时间
     */
    Long startTime;


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(String spendTime) {
        this.spendTime = spendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
}
