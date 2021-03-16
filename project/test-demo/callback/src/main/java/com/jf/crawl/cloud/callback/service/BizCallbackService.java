package com.jf.crawl.cloud.callback.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jf.crawl.cloud.callback.bean.AsyncRequestUtil;
import com.jf.crawl.cloud.callback.constants.CallbackContants;
import com.jf.crawl.cloud.callback.dao.CallbackConfigDao;
import com.jf.crawl.cloud.callback.dao.CallbackLogDao;
import com.jf.crawl.cloud.callback.dao.CallbackModelDao;
import com.jf.crawl.cloud.callback.entity.CallbackLogEntity;
import com.jf.crawl.cloud.callback.entity.CallbackMessage;
import com.mashape.unirest.http.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  租户回调Service类
 * @author liumg
 * @version 创建时间：2018/12/29.
 */
@Component
public class BizCallbackService {

	private static Logger logger = LoggerFactory.getLogger(BizCallbackService.class);

	@Autowired
	CallbackConfigDao callbackConfigDao;

    @Autowired
    CallbackModelDao callbackModelDao;
    
    @Autowired
	CallbackLogDao callbackLogDao;

    @Autowired
	AsyncRequestUtil asyncRequestUtil;

	/**
	 * 消费MQ消息，触发回调
	 * @param message
	 */
    public void handle(final String message) {

		JSONObject messageObject = JSON.parseObject(message);
		JSONObject bizObject = messageObject.getJSONObject("biz");
		CallbackMessage callbackMessage = bizObject.toJavaObject(CallbackMessage.class);
		JSONArray params = messageObject.getJSONArray("params");

		for (int i = 0;i < params.size(); i++) {

			JSONObject item = params.getJSONObject(i);
			String transNo = item.getString("transNo");
			String url = item.getString("url");
			String model = item.getString("model");
			String method = item.getString("method");
			CallbackLogEntity log = new CallbackLogEntity();
			log.setTransNo(transNo);

			JSONObject body = wrapperModelBody(callbackMessage);
			Map<String, Object> requestBody = parseModel(model, body);
			log.setCallbackData(JSONObject.toJSONString(body));
			asyncRequest(url, requestBody, log, callbackMessage,method);
		}
	}
	
	private void asyncRequest(String url, Map<String, Object> body, CallbackLogEntity log, CallbackMessage message, String method) {
		String taskId = message.getTaskId();
		String notifyEvent = message.getNotifyEvent();
		HttpResponse<?> response = null;
		try {
			Map<String, String> headers = new HashMap<>();
			headers.put("service_type",message.getProduct());
			if(StringUtils.isNotBlank(method) && method.equals("form")) {
				logger.info("当前回调流水号为：【{}】,业务回调form开始， 事件：【{}】",
						taskId, notifyEvent);
				 response = asyncRequestUtil.post(url, body, headers);
			}else if(StringUtils.isNotBlank(method) && method.equals("json")){
				logger.info("当前回调流水号为：【{}】,业务回调json开始， 事件：【{}】",
						taskId, notifyEvent);
				 response = asyncRequestUtil.postAsJson(url, body, headers);
			}else{
				logger.info("当前回调流水号为：【{}】,业务回调common开始， 事件：【{}】",
						taskId, notifyEvent);
				response = asyncRequestUtil.post(url, body, headers);
			}
			int httpCode = response.getStatus();
			String content = String.valueOf(response.getBody());

			//回调成功
			log.setStatusCode(httpCode);
			log.setEndTime(new Date());
			log.setSuccess(1);
			log.setStep(CallbackContants.FAILED);
			log.setStatusCode(httpCode);
			logger.info("当前回调流水号为：【{}】,业务回调完成， 事件：【{}】，返回码：【{}】，返回信息：【{}】",
					taskId, notifyEvent, httpCode, content);
			if (httpCode == CallbackContants.SUCCESS_CODE) {
				log.setSuccess(0);
				log.setStep(CallbackContants.SUCCESS);
			}
		} catch (Exception e) {
			String cause = CallbackContants.UNKOWN;
			if (e.getCause() instanceof HttpHostConnectException || e.getCause() instanceof ConnectTimeoutException) {
				log.setStep(CallbackContants.TIMEOUT);
				cause = CallbackContants.TIMEOUT;
			} else if (e.getCause() instanceof SocketTimeoutException) {
				log.setStep(CallbackContants.READOUT);
				cause = CallbackContants.READOUT;
			} else {
				log.setStep(CallbackContants.UNKOWN);
			}
			logger.error("当前回调流水号为：【{}】,业务回调发生异常，异常：",taskId,e);
			logger.info("当前回调流水号为：【{}】,业务回调发生异常，异常：【{}】， 事件：【{}】", taskId,cause, notifyEvent);
		}
		record(log, message);
	}
	
	/**
	 * 记录回调记录流水
	 */
	private void record(CallbackLogEntity logEntity, CallbackMessage callbackMessage) {
		
		String step = logEntity.getStep();
		String appId = callbackMessage.getAppId();
		String taskId = callbackMessage.getTaskId();
		String product = callbackMessage.getProduct();
		String rawBody = callbackMessage.getBody();
		if (CallbackContants.START.equals(step)) {
			logEntity.setAppId(appId);
			logEntity.setCreateTime(new Date());
			logEntity.setEvent(rawBody);
			logEntity.setProduct(product);
			logEntity.setTaskKey(taskId);
			callbackLogDao.insertCallbackLog(logEntity);
		} else {
			callbackLogDao.updateCallbackEventLogByTaskKeyAndTransNo(logEntity);
		}
	}
	
	/**
	 * 检测是否正确的配置
	 * @param url
	 * @return
	 */
	private boolean checkUrl(String url) {

		String httpSchema = "http://";
		String httpsSchema = "https://";

		if (StringUtils.isEmpty(url)) {
			return false;
		}
		
		//校验连接是否合法
		if (url.startsWith(httpSchema) || url.startsWith(httpsSchema)) {
			return true;
		}
		
		return false;
	}

	/**
	 * 回调模型解析
	 * @param model
	 * @param body
	 * @return
	 */
	private Map<String, Object> parseModel(final String model, final JSONObject body) {

		Map<String, Object> requestBody = new HashMap<>(16);
		JSONObject modelBody = JSONObject.parseObject(model);

		//遍历模型中所有key类型
		for(String key : modelBody.keySet()) {
			JSONObject item = modelBody.getJSONObject(key);
			Boolean fixed = item.getBoolean("fixed");
			String pairKey = item.getString("pairKey");
			if (fixed) {
				Object fixedValue = item.get("fixedValue");
				requestBody.put(key, fixedValue);
			} else {
				if (body.containsKey(pairKey)) {
					Object value = body.get(pairKey);
					requestBody.put(key, value);
				}
			}
		}
		
		return requestBody;
	}

	private JSONObject wrapperModelBody(CallbackMessage callbackMessage) {
		JSONObject params = new JSONObject();
		params.put("task_key", callbackMessage.getTaskId());
		params.put("app_id", callbackMessage.getAppId());
		params.put("product", callbackMessage.getProduct());
		params.put("timestamp", String.valueOf(System.currentTimeMillis()));
		params.put("notify_event", callbackMessage.getNotifyEvent());
		if (!StringUtils.isEmpty(callbackMessage.getBody())) {
			params.putAll(JSONObject.parseObject(callbackMessage.getBody()));
		}
		return params;
	}
}
