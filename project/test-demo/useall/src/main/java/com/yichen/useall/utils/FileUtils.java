package com.yichen.useall.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/18 10:04
 * @describe  文件相关的 工具类
 */
public class FileUtils {

    private static Logger logger=LoggerFactory.getLogger(FileUtils.class);

    /**
     *  读取文件 内容以 String 的形式展示   <font color=red>注意，该方法读取文件后，文件中的换行符会丢失，即所有数据均在一行内</font>
     * @param filePath  文件路径
     * @return 文件中的数据，以String 形式展示r
     */
    public static String readFileInString(String filePath){
        StringBuilder builder=new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {
                builder.append(str);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        logger.info("读取 文件"+filePath+"成功");
        logger.info("读取内容为："+builder.toString());
        return builder.toString();
    }

    /**
     *  对多个文件进行合并操作 规则 逐行合并
     * @param outputPath  输出文件名称
     * @param inputPath 输入的合并文件，不限个数
     * @param type 合并规则
     */
    public void mergeFile(String outputPath,int type,String ... inputPath){

    }


}
