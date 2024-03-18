package com.dream.test.threadLocal;

import java.text.SimpleDateFormat;
import java.util.Random;

public class ThreadLocalExample implements Runnable{

    private static final ThreadLocal<SimpleDateFormat>
//            threadLocal = ThreadLocal.withInitial(()-> new SimpleDateFormat("yyyy-MM-dd"));
    threadLocalNew = new ThreadLocal<>();

    private static final ThreadLocal<SimpleDateFormat>
            threadLocalInit = ThreadLocal.withInitial(()-> new SimpleDateFormat("yyyy-MM-dd"));


    public static void main(String[] args) throws InterruptedException {
        ThreadLocalExample example = new ThreadLocalExample();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(example, "thread:" + i);
            Thread.sleep(new Random().nextInt(100));
            thread.start();
        }
    }
    @Override
    public void run() {
        System.out.println("Thread Name= "+Thread.currentThread().getName()+" threadLocalNew 方式："+ (threadLocalNew.get() == null ? "null" : threadLocalNew.get().toPattern()));
        System.out.println("Thread Name= "+Thread.currentThread().getName()+" threadLocalInit 方式： "+threadLocalInit.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
            threadLocalNew.set(new SimpleDateFormat());
            threadLocalInit.set(new SimpleDateFormat());
            System.out.println("Thread Name= "+Thread.currentThread().getName()+" reset threadLocalNew 方式："+ (threadLocalNew.get() == null ? "null" : threadLocalNew.get().toPattern()));
            System.out.println("Thread Name= "+Thread.currentThread().getName()+" reset threadLocalInit 方式： "+threadLocalInit.get().toPattern());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 清除当前线程的ThreadLocalMap中ThreadLocal的值
            threadLocalNew.remove();
            threadLocalInit.remove();
            // 重新获取查看是否已经删除
            System.out.println("Thread Name= "+Thread.currentThread().getName()+" remove threadLocalNew 方式："+ (threadLocalNew.get() == null ? "null" : threadLocalNew.get().toPattern()));
            System.out.println("Thread Name= "+Thread.currentThread().getName()+" remove threadLocalInit 方式： "+threadLocalInit.get().toPattern());

        }
      }
}
