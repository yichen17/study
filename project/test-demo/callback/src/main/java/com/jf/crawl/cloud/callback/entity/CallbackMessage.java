package com.jf.crawl.cloud.callback.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotNull;

/**
 * @author liumg
 * @version 创建时间：2019/7/16.
 */
@ApiModel
public class CallbackMessage {

    @NotNull
    @ApiParam(name = "商户唯一号", required = true)
    private String appId;

    @NotNull
    @ApiParam(name = "业务类型", required = true)
    private String product;

    @NotNull
    @ApiParam(name = "任务号", required = true)
    private String taskId;

    @NotNull
    @ApiParam(name = "回调事件类型", required = true)
    private String notifyEvent;

    @ApiParam(name = "回调扩展参数")
    private String body;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getNotifyEvent() {
        return notifyEvent;
    }

    public void setNotifyEvent(String notifyEvent) {
        this.notifyEvent = notifyEvent;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "{" +
                "appId='" + appId + '\'' +
                ", product='" + product + '\'' +
                ", taskId='" + taskId + '\'' +
                ", notifyEvent='" + notifyEvent + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
