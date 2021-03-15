package com.jf.crawl.cloud.callback.entity;

/**
 *  回调模型实例
 * @author liumg
 * @version 创建时间：2018/12/29.
 */
public class CallbackModelEntity {

	private String appId;
	
	private String partner;
	
	private String method;
	
	private String product;
	
	private String data;
	
	private String event;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMethod() {
		return method;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}
}
