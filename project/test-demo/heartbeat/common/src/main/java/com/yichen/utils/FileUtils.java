package com.yichen.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/10 16:43
 * @describe 文件工具类
 */
@Slf4j
public class FileUtils {
    /**
     * 读取文件数据
     * @param filePath 文件路径-包含地址的绝对路径
     * @return 文本内容，String形式
     */
    public static String readFileByPath(String filePath){
        File file = new File(filePath);
        BufferedReader reader = null;
        String line="";
        StringBuilder builder=new StringBuilder();
        try {
            log.info("开始读取文件，文件名为{}",filePath);
            reader = new BufferedReader(new FileReader(file));
            int row = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("|");
                row++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return builder.toString();
    }


}
