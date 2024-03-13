package com.dream.test.threadLocal;

import java.text.SimpleDateFormat;
import java.util.Random;

public class ThreadLocalExample implements Runnable{

    private static final ThreadLocal<SimpleDateFormat>
            threadLocal = ThreadLocal.withInitial(()-> new SimpleDateFormat("yyyy-MM-dd"));


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
        System.out.println("Thread Name= "+Thread.currentThread().getName()+" default Formatter = "+threadLocal.get().toPattern());
        try {
            Thread.sleep(new Random().nextInt(1000));
            threadLocal.set(new SimpleDateFormat());
            System.out.println("Thread reset Name= "+Thread.currentThread().getName()+" formatter = "+threadLocal.get().toPattern());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 清除当前线程的ThreadLocalMap中ThreadLocal的值
            threadLocal.remove();
            // 使用remove方法立即移除了ThreadLocal的值，这个值仍然会存在于当前线程的内存中。
            //当你再次在该线程中使用get方法获取ThreadLocal的值时，它仍然会返回之前设置的值，除非你再次使用set方法设置一个新的值。
            System.out.println("Thread remove Name= "+Thread.currentThread().getName()+" formatter = "+threadLocal.get().toPattern());
        }
      }
}
