package com.yichen.Reflect;

import com.google.common.collect.Maps;
import com.jcraft.jsch.Session;
import com.yichen.Reflect.utils.SshConnectionTool;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/17 16:13
 * @describe 传输数据，在两个数据库中传输数据
 *    可替代的类似操作：  将不同数据库中的表拷贝到 本地的数据库中  在同一个地方的不同数据库之间可以进行数据操作
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
     * 传输数据 入口，存在两种方式，一种是通过String直接指定两个db的连接信息，另一种是读取配置文件。<br/>
     * 读取配置文件分两种情况，相对路径和绝对路径 <br/>
     * <font color=red>相对路径的情况下，可能会出现读取失败，原因是用户目录可能不同。</font>
     * @param type 0 => 2个字符串的连接url     1 => 绝对路径读取配置文件  2 => 相对路径读取配置文件
     * @param paths 路径
     * @param querySql 查询sql
     * @param tableName 目标插入表名
     * @param config  ssh配置信息
     */
    public static void transmit(int type,String[] paths,String querySql,String tableName,Map<String,String> config){
        // 如果有 ssh 需要用到，手动指定。
//        Map<String,String> sshConfig=new HashMap<>(8);
//        sshConfig.put("sshUsername","daili");
//        sshConfig.put("sshPwd","Ta3l4Q%sDa6G");
//        sshConfig.put("sshPort","22");
//        sshConfig.put("sshHost","192.168.30.186");
//        sshConfig.put("localPort","3309");
//        sshConfig.put("localHost","127.0.0.1");
//        sshConfig.put("remotePort","3306");
//        sshConfig.put("remoteHost","192.168.68.5");

        if(type==0){
            transmit(paths[0],paths[1],querySql,tableName,config);
        }
        // 当前位置
        String position=System.getProperty("user.dir");
        Properties properties=null;
        if(type==1){
            properties = getPropertiesByPath(paths[0]);
        }
        if(type==2){
            logger.info("为相对路径，当前默认文件路径前缀{}",position);
            properties = getPropertiesByPath(paths[0]);
        }
        if(properties==null){
            logger.info("读取配置文件失败，请核对配置文件位置");
            return ;
        }
        String from=properties.get("db1.url")+"|"+properties.get("db1.username")+"|"+properties.get("db1.pwd");
        String to=properties.get("db2.url")+"|"+properties.get("db2.username")+"|"+properties.get("db2.pwd");
        Map<String,String> sshConfig=new HashMap<>(8);
        sshConfig.put("sshUsername", (String) properties.get("sshUsername"));
        sshConfig.put("sshPwd", (String) properties.get("sshPwd"));
        sshConfig.put("sshPort", (String) properties.get("sshPort"));
        sshConfig.put("sshHost", (String) properties.get("sshHost"));
        sshConfig.put("localPort", (String) properties.get("localPort"));
        sshConfig.put("localHost", (String) properties.get("localHost"));
        sshConfig.put("remotePort", (String) properties.get("remotePort"));
        sshConfig.put("remoteHost", (String) properties.get("remoteHost"));
        transmit(from,to,querySql,tableName,sshConfig);
    }


    /**
     *  用来将 一个数据库中查询出来的数据 插入到另一个数据库中。
     *  这里是正常使用账号密码连接数据库实例    数据库实例是通过字符串 url来解析，以 `|`划分
     *  示例：  jdbc:mysql://localhost:3307/transmit?serverTimezone=Asia/Shanghai|root|123
     *  TODO querySql 只考虑简单查询，复合查询暂时未考虑
     *  TODO 只实现了 mysql数据库
     *  TODO 只考虑到了单个 ssh，没考虑多个ssh。
     * @param connFrom 数据源 mysql配置信息
     * @param connTo 目的地 mysql 配置信息
     * @param querySql 查询sql   这里指定为 select 查询
     * @param tableName 目标插入表名
     */
    public static void transmit(String connFrom,String connTo,String querySql,String tableName,Map<String,String> sshConfig){
        SshConnectionTool tool=null;
        // 先建立  ssh 通道
        try{
            if(sshConfig!=null&&sshConfig.size()==8){
                tool = buildSshSession(sshConfig.get("sshUsername"),sshConfig.get("sshPwd"),Integer.parseInt(sshConfig.get("sshPort"))
                        ,sshConfig.get("sshHost"),Integer.parseInt(sshConfig.get("localPort")),
                        sshConfig.get("localHost"),Integer.parseInt(sshConfig.get("remotePort")),sshConfig.get("remoteHost"));
            }
        }
        catch (Throwable throwable){
            logger.warn("ssh 桥接建立失败，错误内容为{}",throwable.getMessage());
        }

        // 构造数据库连接
        Connection from=buildConnection(connFrom);
        Connection to=buildConnection(connTo);
        // 截取查询字段
        String queryField=getQueryField(querySql);
        List<String> fields=getFields(queryField);
        // 动态构建类，单次构建重复使用
        Object object = dynamicConstructObject(fields);
        structClass=object.getClass();
        // 查询源数据库获取结果集
        List<Object> data = queryDataFromDb(from, querySql, fields);
        // 将数据插入目标数据库
        for(Object o:data){
            String sql=constructSql(fields,o,tableName);
            insertDataToDb(sql,to);
        }
        // 执行关闭 关闭 ssh
        if(tool!=null){
            tool.closeSsh();
        }
        logger.info("已成功执行完毕");
    }


    /**
     * 构建 ssh 连接实例,根据情况自己填充数据
     * @param sshUsername ssh 用户名
     * @param sshPwd ssh 密码
     * @param sshPort ssh 端口
     * @param sshHost  ssh ip地址
     * @param localPort  本机端口，用于ssh转发
     * @param localHost  本机 ip地址
     * @param remotePort 远程端口
     * @param remoteHost 远程ip地址
     * @return ssh连接实例,用于之后关闭 ssh
     * @throws Throwable ssh建立连接异常
     */
    public static SshConnectionTool buildSshSession(String sshUsername,String sshPwd,int sshPort,String sshHost,
                                                    int localPort,String localHost,int remotePort,String remoteHost) throws Throwable {
        logger.info("buildSshSession入参:sshUsername:{},sshPwd:{},sshPort:{},sshHost:{},localPort:{},localHost:{},remotePort:{},remoteHost:{}",
                sshUsername,sshPwd,sshPort,sshHost,localPort,localHost,remotePort,remoteHost);
        SshConnectionTool tool=new SshConnectionTool();
        tool.setRemoteSshProperties(sshUsername,sshPwd,sshPort,sshHost);
        tool.setForwardProperties(localPort,localHost,remotePort,remoteHost);
        tool.getSession();
        return tool;
    }

    /**
     * TODO 字段填充类型指定为 字符串，待优化，扩充：区分数值和字符串    mysql5.7 貌似存在自动转换机制， int、decimal 传入 字符串也会自动转换。
     * 构造 查询 sql
     * @param fields 数据字段
     * @param o 实例数据
     * @param tableName 插入目标表名
     * @return 生成的 sql语句
     */
    public static String constructSql(List<String> fields,Object o,String tableName){
        StringBuilder builder=new StringBuilder();
        builder.append("insert into ").append(tableName).append("(");
        for(String field:fields){
            builder.append(field).append(",");
        }
        builder.replace(builder.length()-1,builder.length(),")");
        builder.append("values (");
        for(String field:fields){
            Object value=getFieldValueByName(field,o);
            // 可以需要根据情况排查。
            //  1、数字、decimal 等可以直接用字符串插入，mysql 会自动转换(自己测试 5.7)
            //  2、如果 值为空，则直接插入null，不再加 '' ，不然会报错
            if(value==null){
                builder.append(value).append(",");
            }
            else{
                builder.append("'").append(value).append("',");
            }

        }
        builder.replace(builder.length()-1,builder.length(),");");
        logger.info("字段名称{},表名{},构建的sql为{}",fields,tableName,builder.toString());
        return builder.toString();
    }

    /**
     * 执行sql
     * @param sql 需要运行的 sql
     * @param conn 数据库连接实例
     */
    public static void insertDataToDb(String sql,Connection conn){
        try{
            Statement statement=conn.createStatement();
            int i = statement.executeUpdate(sql);
            if(i==0){
                logger.warn("执行插入操作异常，影响行数为0");
            }
            else{
                logger.info("执行插入成功,sql为{}",sql);
            }
        }
        catch (SQLException e ) {
            logger.warn("查询数据库异常，查询sql为{}，异常信息为{}", sql, e.getMessage());
        }
    }




    /**
     * 使用sql 查询db
     * @param conn 数据库连接实例
     * @param sql 查询sql
     * @param fields 查询字段，用来填充动态创建的实例
     * @return 查询db后返回的结果集
     */
    public static List<Object> queryDataFromDb(Connection conn,String sql,List<String> fields){
        List<Object> res=new ArrayList<>();
        try{
            Statement statement=conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            // 根据字段 动态构造的类对象
            Object object;
            while(resultSet.next()){
                object=structClass.newInstance();
                for(String field:fields){
                    setFieldValueByName(field, resultSet.getString(field),object );
                }
                res.add(object);
            }
        }
        catch (SQLException e ){
            logger.warn("查询数据库异常，查询sql为{}，异常信息为{}",sql,e.getMessage());
        } catch (IllegalAccessException | InstantiationException e) {
            logger.warn("创建实例异常,内容为{}",e.getMessage());
        }
        logger.info("查询结果为{}",res);
        return res;
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
     * 打印动态生成对象
     * @param object 动态生成对象
     * @param fields 对应的字段
     */
    public static void printObject(Object object,List<String> fields){
        for(String field:fields){
            System.out.println(getFieldValueByName(field,object));
        }
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
     * TODO 存在情况 字段中有 as 字符问题  mysql 5.5.58貌似不支持 as 别名
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
        logger.info("获取的查询列信息{}", res);
        return res;
    }

    /**
     * 根据查询 sql截取其中的查询字段
     * @param querySql 查询sql
     * @return 返回查询sql中的查询字段
     */
    public static String getQueryField(String querySql){
        logger.info("getQueryField方法入参{}",querySql);
        // 统一转为小写，避免出错
        querySql=querySql.toLowerCase();
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
        logger.info("数据库已连接");
        return connection;
    }

    /**
     * 读取指定位置的配置文件
     * @param path 配置文件放置的地方
     * @return 读取配置文件的实例
     */
    public static Properties getPropertiesByPath(String path){
        Properties properties= null;
        try{
//            properties=PropertiesLoaderUtils.loadProperties(new FileSystemResource(path));
            // 可以执行指定路径
            File file=new File(path);
            if(!file.exists()){
                logger.info("该路径下面没有对应的配置文件，请核对路径{}",file.getAbsolutePath());
                return null;
            }
            properties=PropertiesLoaderUtils.loadProperties(new FileSystemResource(file));
            logger.info("读取properties成功");
        }
        catch (IOException e){
            logger.warn("读取db配置文件失败,配置文件所在位置{}",path);
        }
        return properties;
    }



    public static void main(String[] args){


//        System.out.println(getPropertiesByPath("F:\\data\\db.properties").get("db1.username"));
        System.out.println(getPropertiesByPath(".\\test\\src\\main\\resources\\db.properties").get("aa"));

//        transmit("jdbc:mysql://127.0.0.1:3309/fincloud_loan_order_test?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT|fincloud_loan_rw|ZwSMBGTuAtqx",
//                "jdbc:mysql://localhost:3307/test?serverTimezone=Asia/Shanghai|root|123",
//                "SELECT id, merch_no, asset_product_id, inst_no, fund_product_id, order_id, jf_apply_id, third_trans_no, fund_provide_product_id, third_loan_invoice_id, loan_date, deal_result, deal_desc, apply_num, query_num, remark, is_delate, drawn_amt, trans_no, apply_loan_amt, launch_code, bus_batch_id, old_id, apply_loan_date, safeguard_type, value_date  FROM t_order_loan_apply limit 10","t_order_loan_apply_fincloud");



//        System.out.println(getFields("jf_amount as amount , jf_merce merce,as,fdsfdas"));
//        System.out.println(getQueryField("select name ,age ,sex from user"));

        // 测试动态构建类
//        Object object = dynamicConstructObject(getFields("jf_amount as amount , jf_merce merce,as,fdsfdas"));
//        setFieldValueByName("amount","7421",object);
//        System.out.println(getFieldValueByName("amount", object));

//        String querySql="select id as no,name from auto_incre";
//        Connection connection = buildConnection("jdbc:mysql://localhost:3307/test?serverTimezone=Asia/Shanghai|root|123");
//        String queryField=getQueryField(querySql);
//        List<String> fields=getFields(queryField);
//        // 动态构建类，单次构建重复使用
//        Object object = dynamicConstructObject(fields);
//        structClass=object.getClass();
//        List<Object> objects = queryDataFromDb(connection, querySql, fields);
//        for(Object o:objects){
////            printObject(o,fields);
//            System.out.println(constructSql(fields,o,"user"));
//        }


    }

}