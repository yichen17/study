package com.yichen.test;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/6/4 17:16
 * @describe  测试threadLocal 不用的时候释放它
 */
public class TestThreadLocal {

    /**
     * java  对于没有指定访问权限的变量默认的访问权限是 protected，即子类，自身以及同包下的类可以访问
     */
    static String range;

    public static void main(String[] args)throws Exception {

//        System.out.println(TestThreadLocal.class.getDeclaredField("range").isAccessible());

        ThreadLocal<String> threadLocal=new ThreadLocal<>();
        System.out.println(Thread.currentThread());
        threadLocal.set("123");
        System.out.println(threadLocal.get());
        // remove 只是移除了 threadLocal中存储的内容，它本身仍是存在的。如果想要释放它，将它指定为null即可，此时 gc()会回收它。
        threadLocal.remove();
        System.out.println(threadLocal.get());



    }

}
