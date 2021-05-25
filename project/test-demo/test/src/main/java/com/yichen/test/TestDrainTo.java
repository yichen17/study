package com.yichen.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/5/25 13:52
 * @describe  测试逻辑，阻塞队列中取出一个数，然后 drainTo到另一个 数组，在向数组中添加取出来的数
 *   测试结果  原队列中数组  1,2,3,4  处理后的结果   2,3,4,1  且queue 清空
 */
public class TestDrainTo {


    public static void main(String[] args) {
        LinkedBlockingQueue<Integer> queue=new LinkedBlockingQueue<>();
        ArrayList<Integer>list=new ArrayList<>();
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        queue.offer(4);
        try{
            Integer top=queue.take();
            queue.drainTo(list);
            list.add(top);
            System.out.println(list.toString());
            System.out.println(queue.size());
        }catch (Exception e){

        }






    }
}
