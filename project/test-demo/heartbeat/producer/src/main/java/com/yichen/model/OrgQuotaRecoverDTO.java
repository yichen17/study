package com.yichen.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class OrgQuotaRecoverDTO implements Serializable {
	/**
	 * 交易流水号
	 */
	private String transNo;
	/**
	 * 工单号
	 */
	private String appId;
	/**
	 * 还款期次
	 */
	private String stage;
	/**
	 * 还款金额
	 */
	private String repayAmt;
	/**
	 * 还款流水的还款状态
	 */
	private String repayStatus;
	/**
	 * 还款类型
	 */
	private String repayType;
	/**
	 * 工单状态
	 */
	private String orderRepayStatus;
	/**
	 * 工单是否结清  0或空：未结清   1:已结清
	 */
	private String isSettle;

}
