package com.yichen.test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/12 9:29
 * @describe 测试 对比运行时间的差异
 *      具体内容，逐个对比数组中每一项的内容  同 将数组内容构建成字符串 进行比对，两者运行时间的差异
 */
public class TestRunTime {
    public static void main(String[] args)throws Exception {

        List<Integer> data=new ArrayList<>(4194304);
        for(int i=0;i<4194304;i++){
            data.add((int)(Math.random()*(i+10)));
        }
        Integer a[]=data.toArray(new Integer[0]);
        Integer b[]=data.toArray(new Integer[0]);
        int i=0,len=a.length;
        String res;
        StringBuilder r1=new StringBuilder();
        StringBuilder r2=new StringBuilder();
        String s1="";
        String s2="";
        long start=System.currentTimeMillis();
//        for(i=0,len=a.length;i<len;i++){
//            if(a[i].equals(b[i])){
//                continue;
//            }
//            else{
//                break;
//            }
//        }
//        res=i==len?"一样":"不一样";


        for(i=0,len=a.length;i<len;i++){
            r1.append("|"+a[i]);
            r2.append("|"+b[i]);
        }
        res=r1.toString().equals(r2.toString())?"一样":"不一样";
//        res=r1.toString()==r2.toString()?"一样":"不一样";

//        for(i=0,len=a.length;i<len;i++){
//            s1=s1+"|"+a[i];
//            s2=s2+"|"+b[i];
//        }
//        res=s1.equals(s2)?"一样":"不一样";

        long stop=System.currentTimeMillis();
        System.out.println("cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        r1=new StringBuilder();
        r2=new StringBuilder();
        start=System.currentTimeMillis();
        for(i=0,len=a.length;i<len;i++){
            r1.append("|"+a[i]);
            r2.append("|"+b[i]);
        }
        res=r1.toString()==r2.toString()?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("== cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        r1=new StringBuilder();
        r2=new StringBuilder();
        start=System.currentTimeMillis();
        for(i=0,len=a.length;i<len;i++){
            r1.append("|"+a[i]);
            r2.append("|"+b[i]);
        }
        res=r1.toString().equals(r2.toString())?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("equals cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        r1=new StringBuilder();
        r2=new StringBuilder();
        start=System.currentTimeMillis();
        for(i=0,len=a.length;i<len;i++){
            r1.append("|"+a[i]);
            r2.append("|"+b[i]);
        }
        res=r1.toString()==r2.toString()?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("== cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        r1=new StringBuilder();
        r2=new StringBuilder();
        start=System.currentTimeMillis();
        for(i=0,len=a.length;i<len;i++){
            r1.append("|"+a[i]);
            r2.append("|"+b[i]);
        }
        res=r1.toString().equals(r2.toString())?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("equals cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        r1=new StringBuilder();
        r2=new StringBuilder();
        start=System.currentTimeMillis();
        for(i=0,len=a.length;i<len;i++){
            r1.append("|"+a[i]);
            r2.append("|"+b[i]);
        }
        res=r1.toString()==r2.toString()?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("== cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        r1=new StringBuilder();
        r2=new StringBuilder();
        start=System.currentTimeMillis();
        for(i=0,len=a.length;i<len;i++){
            r1.append("|"+a[i]);
            r2.append("|"+b[i]);
        }
        res=r1.toString().equals(r2.toString())?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("equals cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        r1=new StringBuilder();
        r2=new StringBuilder();
        start=System.currentTimeMillis();
        for(i=0,len=a.length;i<len;i++){
            r1.append("|"+a[i]);
            r2.append("|"+b[i]);
        }
        res=r1.toString()==r2.toString()?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("== cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        r1=new StringBuilder();
        r2=new StringBuilder();
        start=System.currentTimeMillis();
        for(i=0,len=a.length;i<len;i++){
            r1.append("|"+a[i]);
            r2.append("|"+b[i]);
        }
        res=r1.toString().equals(r2.toString())?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("equals cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        System.out.println("===============================不摧毁StringBuilder进行对比=====================================");

        start=System.currentTimeMillis();
        res=r1.toString().equals(r2.toString())?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("equals cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();


        start=System.currentTimeMillis();
        res=r1.toString()==r2.toString()?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("== cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        start=System.currentTimeMillis();
        res=r1.toString().equals(r2.toString())?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("equals cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        start=System.currentTimeMillis();
        res=r1.toString()==r2.toString()?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("== cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        start=System.currentTimeMillis();
        res=r1.toString().equals(r2.toString())?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("equals cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        start=System.currentTimeMillis();
        res=r1.toString()==r2.toString()?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("== cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        start=System.currentTimeMillis();
        res=r1.toString().equals(r2.toString())?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("equals cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        start=System.currentTimeMillis();
        res=r1.toString()==r2.toString()?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("== cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        start=System.currentTimeMillis();
        res=r1.toString().equals(r2.toString())?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("equals cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();

        start=System.currentTimeMillis();
        res=r1.toString()==r2.toString()?"一样":"不一样";
        stop=System.currentTimeMillis();
        System.out.println("== cost time :"+ (stop-start));
        System.out.println("result: "+ res);
        System.gc();


//        System.out.println(Arrays.toString(data.toArray()));
    }

    public static void  outResultToFile(String result){
        FileWriter fileWriter=null;
        try{
            fileWriter=new FileWriter("./test.txt",true);
            fileWriter.write(result);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                fileWriter.flush();
                fileWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}
