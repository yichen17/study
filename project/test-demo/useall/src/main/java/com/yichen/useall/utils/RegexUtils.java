package com.yichen.useall.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/18 9:35
 * @describe   通过正则表达式  产生的相关工具方法
 */
public class RegexUtils {

    private static Logger logger= LoggerFactory.getLogger(RegexUtils.class);

    /**
     *
     * @param data  执行正则表达式过滤的数据源
     * @param expression  正则表达式
     * @return 符合正则表达式的结果集
     */
    public static String getDateByRegex(String data,String expression){
        Pattern r=Pattern.compile(expression);
        Matcher m=r.matcher(data);
        StringBuilder res=new StringBuilder();
        // groupCount 即表示 正则表达式  帅选的组数，对应几个 ()
        int groupCount=m.groupCount();
        logger.info("一种有"+groupCount+"捕获的组数");
        int count=0;
        while(m.find()){
            count++;
            // 组数从1开始，0 保存的是 整个模式
            for(int i=1;i<=groupCount;i++){
                res.append(m.group(i));
                res.append("-");
            }
            res.replace(res.length()-1,res.length(),"\n");
        }
        logger.info("执行结果：一共有"+count+"条匹配\n"+res.toString());
        return res.toString();
    }

    public static void main(String[] args) {
        String data=FileUtils.readFileInString("D:\\personal\\learn note\\project\\test-demo\\useall\\src\\main\\resources\\utilsTest\\regex-test.txt");
        System.out.println(getDateByRegex(data,"\\\"(\\w*-?\\w*)\\\"\\:\\{\\s*\\\"\\w*.{4}(\\w*-?\\w*).{2}"));
    }

}
