package com.jf.crawl.cloud.callback.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Eric Lau
 * @description 回调配置
 * @time 下午7:53:16
 */

public class CallbackConfigEntity {
    
	@JSONField(name = "id")
	private Integer id;
	
	@JSONField(name = "product")
    private String product;

    /**
     * 租户唯一标识
     */
    @JSONField(name = "appid")
    private String appId;

    /**
     * 登录成功回调头
     */
    @JSONField(name = "partner")
    private String partner;
    
    /**
     * 租户自定义头
     */
    @JSONField(name = "headers")
    private String headers;

    @JSONField(name = "notify_evnet")
    private String notifyEvent;

    /**
     * 登录成功回调地址
     */
    @JSONField(name = "url")
    private String url;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotifyEvent() {
        return notifyEvent;
    }

    public void setNotifyEvent(String notifyEvent) {
        this.notifyEvent = notifyEvent;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteNullStringAsEmpty);
    }
}
