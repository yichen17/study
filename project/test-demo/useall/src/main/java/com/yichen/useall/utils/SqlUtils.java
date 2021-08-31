package com.yichen.useall.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/30 16:58
 * @describe mysql 数据库相关的工具类
 *   用途：根据 mysql表构建 其他服务的 model 类供 自己进行 单元测试
 *   1、在 navicat 中找到对应表，选择一条记录，右键行前选择 复制 insert 语句
 *   2、将语句放于 下面类的 main 中，运行即可生成对应的 由 fastjson 转换而成的 string
 *   3、将运行的结果放于 单元测试中，通过 JSONObject.parseObject() 生成对象
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
            res.put(FormatUtils.lineToHump(fields[i].trim()),values[i].trim());
        }
        // TODO 这里存在一个缺陷， NULL 类型被加了 双引号，需要手动转换，不然无法转回对象
        String r=JSONObject.toJSONString(res);

        //  去掉null 前后的 双引号
        r=r.replace("\"NULL\"","NULL");

        logger.info("转换后的结果{}",r);
        return r;
    }




    public static void main(String[] args) {
        constructMapFromInsertSql("INSERT INTO `wk_db_user`.`wk_customer`(`customerId`, `name`, `certId`, `source`, `birthDay`, `sex`, `tradePwd`, `createTime`, `updatedTime`, `serial_no`, `certIdphoto`, `pinyin`, `address`, `authority`, `timelimit`, `mobile`, `tenant_id`, `proId`, `nationality`) VALUES (1170262913, '杨云鹏', '140481199003136811', 1799, '1990-03-13', 1, '', '2021-08-30 16:59:06', '2021-08-30 16:59:06', NULL, NULL, 'yangyunpeng', NULL, NULL, NULL, '15601292370', 1001, '360sjzs8a518bd623ddbecbb2b2a3bb7f640e30', NULL);\n");

    }

}
