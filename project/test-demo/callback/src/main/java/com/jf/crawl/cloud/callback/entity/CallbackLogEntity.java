package com.jf.crawl.cloud.callback.entity;

import java.util.Date;

/**
 * @description 租户回调记录
 * @author Eric Lau
 * @time 上午10:05:32
*/

public class CallbackLogEntity {

	long id;

	/**
	 * 租户appId
	 */
	private String appId;
	
	/**
	 * 产品类型
	 */
	private String product;
	
	/**
	 * 任务号
	 */
	private String taskKey;
	
	/**
	 * 通知时间
	 */
	private String notifyEvent;
	
	/**
	 * 通知时间
	 */
	private Date updateTime;
	
	/**
	 * 是否成功
	 */
	private Integer success;
	
	/**
	 * 回调对接渠道来源
	 */
	private String fromChannel;
	
	/**
	 * 回调步骤，START, SUCCESS, FAILED, CANCELLED, INVOKE
	 */
	private String step;
	
	/**
	 * 回调流水号
	 */
	private String transNo;
	
	/**
	 * 回调事件数据
	 */
	private String event;
	
	/**
	 * 是否商戶
	 */
	private String isManual;
	
	/**
	 * 下游回调数据
	 */
	private String callbackData;
	
	/**
	 * 回调开始时间
	 */
	private Date createTime;
	
	/**
	 * 回调触发时间
	 */
	private Date invokeTime;
	
	/**
	 * 回调完成时间
	 */
	private Date endTime;
	
	/**
	 * 最后一次回调，业务方返回码
	 */
	private Integer statusCode;
	
	/**
	 * 回调次数
	 */
	private Integer callbackTimes;
	
	/**
	 * 商户合作id
	 */
	private String partner;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getNotifyEvent() {
		return notifyEvent;
	}

	public void setNotifyEvent(String notifyEvent) {
		this.notifyEvent = notifyEvent;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFromChannel() {
		return fromChannel;
	}

	public void setFromChannel(String fromChannel) {
		this.fromChannel = fromChannel;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getIsManual() {
		return isManual;
	}

	public void setIsManual(String isManual) {
		this.isManual = isManual;
	}

	public String getCallbackData() {
		return callbackData;
	}

	public void setCallbackData(String callbackData) {
		this.callbackData = callbackData;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getInvokeTime() {
		return invokeTime;
	}

	public void setInvokeTime(Date invokeTime) {
		this.invokeTime = invokeTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getCallbackTimes() {
		return callbackTimes;
	}

	public void setCallbackTimes(Integer callbackTimes) {
		this.callbackTimes = callbackTimes;
	}

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CallbackLogEntity copy() {
		CallbackLogEntity copy = new CallbackLogEntity();
		copy.setAppId(appId);
		copy.setTaskKey(taskKey);
		copy.setNotifyEvent(notifyEvent);
		copy.setProduct(product);
		copy.setStep(step);
		return copy;
	}

	@Override
	public String toString() {
		return "TenantCallbackLog [appId=" + appId + ", taskKey=" + taskKey + ", notifyEvent=" + notifyEvent
				+ ", updateTime=" + updateTime + ", isSuccess=" + success + ", fromChannel=" + fromChannel + "]";
	}
	
}
