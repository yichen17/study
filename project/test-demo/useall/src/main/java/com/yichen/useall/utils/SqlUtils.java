package com.yichen.useall.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/30 16:58
 * @describe mysql 数据库相关的工具类
 */

public class SqlUtils {

    private static Logger logger= LoggerFactory.getLogger(SqlUtils.class);

    /**
     * 根据 insert sql 构造 json格式 数据字符串
     * <font color=red>这里的sql 必然是简单插入.也不存在列取别名</font>
     * @param insertSql insert 类型的数据sql
     * @return json 转换而来的 字符串
     */
    public static String constructMapFromInsertSql(String insertSql){
        String[] a=insertSql.replace("(","|").split("\\|");
        String [] b=a[1].replace(")","|").split("\\|");
        String []c=a[2].replace(")","|").split("\\|");
        String []fields=b[0].replace("`","").split(",");
        String []values=c[0].replace("'","").split(",");
        JSONObject res=new JSONObject();
        for(int i=0;i<fields.length;i++){
            res.put(fields[i].trim(),values[i].trim());
        }
        String r=JSONObject.toJSONString(res);
        logger.info("转换后的结果{}",r);
        return r;
    }

    public static void main(String[] args) {
        constructMapFromInsertSql("INSERT INTO `wk_db_loan`.`wk_loan_order`(`app_id`, `account_id`, `bus_code`, `customer_id`, `customer_name`, `third_bank_info`, `customer_certid`, `phone_no`, `bank_card_no`, `cardholder`, `bank_name`, `purpose`, `address`, `app_status`, `repay_status`, `product_id`, `product_type`, `product_name`, `charge_rate`, `exceed_rate`, `service_fee_rate`, `risk_fee_rate`, `finan_rate`, `contact_year_rate`, `contact_month_rate`, `origin_ratio`, `adjust_ratio`, `fee_rate`, `contact_amt`, `month_amt`, `loan_principal`, `receive_bank`, `receive_bank_no`, `loan_date`, `loan_out_date`, `real_repay_date`, `plan_repay_date`, `loan_time_limit`, `real_time_limit`, `plan_repay_fee_term`, `is_get_refund_plan`, `urgent_code`, `urgent_status`, `is_allot`, `max_exceed_date`, `exceed_began_date`, `hava_exceed`, `create_time`, `handle_time`, `auditor`, `advice`, `mender`, `mend_time`, `pay_time`, `pro_id`, `source`, `loanType`, `credit_type`, `processing`, `remark`, `repayDay`, `old_bus_code`, `order_no`, `batch_id`, `cust_no`, `walletType`, `fire_code`, `minuteUnique`, `is_used_red_packet`, `bus_status`, `capital_type`, `relative_id`, `tenant_id`) VALUES (470110, 1149354, '226325327', 114737634, '孙国辉', NULL, '230302196706304014', '13189758881', '6226220622936614', '孙国辉', 'CMBC', 'F11120', '广东深圳市龙岗区丹竹头村树山街九号', -1, 0, 298, 2, '万普分期', 0.080, 0.001, NULL, NULL, NULL, 0.1281, NULL, 9.000, 2.000, 0.0800, 1713.64, 578.67, 1400.00, 'CMBC', '6226220622936614', '2017-05-23 06:36:03', '2018-11-01 17:31:30', NULL, '0000-00-00 00:00:00', 3, NULL, NULL, '1', NULL, NULL, '1', NULL, NULL, '1', '2020-06-03 00:00:00', NULL, NULL, NULL, '执行产品服务【进件规则校验服务】失败，失败原因：客户【孙国辉】已申请过贷款，不允许重复申请', '2020-10-27 17:31:34', '2018-11-01 11:40:30', '99db019544731fbdcb78c77d1d411bf4d98', 1064, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '201705230629386312199889877', 0, '462', '470110', '0', NULL, '0', '0', 1001);\n");
    }

}
