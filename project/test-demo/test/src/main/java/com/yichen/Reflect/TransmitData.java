package com.yichen.Reflect;

import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/17 16:13
 * @describe 传输数据，在两个数据库中传输数据
 *  *   step:
 *  *      1、获取两个数据库连接
 *  *      2、根据查询sql 获取字段名称
 *  *      3、根据字段名称动态构建类  保存该类之后不在创建直接复用
 *  *      4、查询数据写入类，读取数据写入数据库
 */
public class TransmitData {

    private static Logger logger= LoggerFactory.getLogger(TransmitData.class);

    private static Class structClass=null;

    /**
     *  用来将 一个数据库中查询出来的数据 插入到另一个数据库中
     *  TODO 待解决，如果涉及到 SSH通道，怎么处理
     *  TODO querySql 只考虑简单查询，符合查询暂时未考虑
     *  TODO 查询字段即为列名，别名暂未考虑
     * @param conn_from 数据源 mysql配置信息
     * @param conn_to 目的地 mysql 配置信息
     * @param querySql 查询sql   这里指定为 select 查询
     */
    public static void transmit(String conn_from,String conn_to,String querySql){
        // 构造数据库连接
        Connection from=buildConnection(conn_from);
        Connection to=buildConnection(conn_to);
        // 截取查询字段
        String queryField=getQueryField(querySql);
        List<String> fields=getFields(queryField);
        // 动态构建类，单次构建重复使用
        Object object = dynamicConstructObject(fields);
        structClass=object.getClass();


    }

    /**
     * 动态构建类，添加属性
     * @param fields 属性数组
     * @return 添加属性数组内容后的新对象
     */
    public static Object dynamicConstructObject(List<String> fields){
        Map<String, Class> propertyMap = Maps.newHashMap();
        // add extra properties
        for (String field:fields) {
            propertyMap.put(field, String.class);
        }
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(Object.class);
        BeanGenerator.addProperties(generator, propertyMap);
        return generator.create();
    }

    /**
     * 向构建类设置属性
     * @param fieldName 字段名称
     * @param fieldValue 字段数据
     * @param object 类对象
     */
    public static void setFieldValueByName(String fieldName,String fieldValue,Object object){
        try {
            //通过反射获取值
            Method method = object.getClass().getMethod(constructMethodName(1,fieldName),String.class);
            method.invoke(object, fieldValue);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 构建 set 和 get 方法名
     * @param type 构建类型，0=get、1=set
     * @param fieldName 字段名称
     * @return 正常的get、set方法名称
     */
    public static String constructMethodName(int type,String fieldName){
        if(type==0){
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            return "get" + firstLetter + fieldName.substring(1);
        }
        if(type==1){
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            return  "set" + firstLetter + fieldName.substring(1);
        }
        return "";
    }


    /**
     *  读取构建对象数据
     * @param fieldName 字段名称
     * @param o 构建类对象
     * @return 对应字段的数据
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            //通过反射获取值
            Method method = o.getClass().getMethod(constructMethodName(0,fieldName));
            return method.invoke(o);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }


    /**
     * TODO 存在情况 字段中有 as 字符问题
     * @param queryFields 未切分的查询字段
     * @return 切分后的查询字段数组
     */
    public static List<String> getFields(String queryFields){
        String [] fields=queryFields.split(",");
        List<String> res=new ArrayList<>(fields.length);
        int pos;
        int size;
        // 是否已添加
        boolean addFlag;
        for(String field:fields){
            addFlag=false;
            // 去除左右两端空格
            field=field.trim().toLowerCase();
            pos=field.indexOf("as");
            // 由于 字段名称也可能包含 as  故需要进行判断
            if(pos!=-1){
                size=field.length();
                while(pos<size&&pos!=-1){
                    if(pos==0){
                        pos=field.indexOf("as",pos+1);
                    }
                    else{
                        if(field.charAt(pos-1)==' '){
                            if(pos+2==size){
                                // 这种情况是 as 在字段末尾
                                break;
                            }
                            if(field.charAt(pos+2)==' '){
                                // 前后均为空格
                                res.add(field.substring(pos+2).trim());
                                addFlag=true;
                                break;
                            }
                        }
                        //  其他情况 后移查询下一个  as
                        pos=field.indexOf("as",pos+1);
                    }
                }

            }
            if(addFlag){
                continue;
            }
            // 查询空格，有空格说明 取了别名，取后者即可
            pos=field.indexOf(" ");
            if(pos!=-1){
                res.add(field.substring(pos).trim());
                continue;
            }
            // 只有一个列字段，直接添加
            res.add(field);
        }
        return res;
    }

    /**
     * 根据查询 sql截取其中的查询字段
     * @param querySql 查询sql
     * @return 返回查询sql中的查询字段
     */
    public static String getQueryField(String querySql){
        int selectPos=querySql.indexOf("select");
        int fromPos=querySql.indexOf("from");
        String queryField=querySql.substring(selectPos+6,fromPos).trim();
        logger.info("查询sql为{},截取其中的查询字段为{}",querySql,queryField);
        return queryField;
    }




    /**
     * 根据提供的mysql连接信息返回连接实例
     * @param conn mysql 连接信息
     * @return mysql连接实例，如果连接失败返回空
     */
    public static Connection buildConnection(String conn){
        String []datas=conn.split("\\|");
        // "jdbc:mysql://localhost:3306/test_demo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC","root","password"
        Connection connection=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(datas[0],datas[1],datas[2]);
        } catch (ClassNotFoundException | SQLException e) {
            logger.warn("连接数据库异常，异常信息为{}",e.getMessage());
        }
        return connection;
    }


    public static void main(String[] args) {



//        System.out.println(getFields("jf_amount as amount , jf_merce merce,as,fdsfdas"));
//        System.out.println(getQueryField("select name ,age ,sex from user"));

        // 测试动态构建类
//        Object object = dynamicConstructObject(getFields("jf_amount as amount , jf_merce merce,as,fdsfdas"));
//        setFieldValueByName("amount","7421",object);
//        System.out.println(getFieldValueByName("amount", object));

    }

}
