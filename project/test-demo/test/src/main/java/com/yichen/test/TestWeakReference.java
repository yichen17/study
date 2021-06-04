package com.yichen.test;

import java.lang.ref.WeakReference;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/6/4 17:06
 * @describe   测试 弱引用被gc回收的具体概念
 *   如果 某个对象只存在弱引用，则gc的时候会回收对象，如果存在弱引用以及  强引用或者虚引用 则不会被gc回收
 */
public class TestWeakReference {

    private static WeakReference<String> reference;

    public static void main(String[] args) {
        createStrongReference();
        System.out.println("此时返回方法后，字符串s的强引用已经消失，只存在虚引用，此时虚引用的值："+reference.get());
        System.gc();
        System.out.println("gc()回收后，此时虚引用的值："+reference.get());



    }

    public static void createStrongReference(){
        String s=new String("aaa");
        reference=new WeakReference<>(s);
        System.out.println("存在强引用和弱引用同时指向 字符串s时，虚引用的值："+reference.get());
        System.gc();
        System.out.println("存在强引用和弱引用同时指向 字符串s时,进行gc()回收后，虚引用的值："+reference.get());
    }

}
