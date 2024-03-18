package com.dream.spi.thread;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * @author @四维穿梭
 */
public class ThreadCreate {


    /**
     *  创建线程的方式一：继承Thread类
     */
    static class ByThread extends Thread {

        private String name;

        public ByThread(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println(name + Thread.currentThread().getName());
        }
    }

    /**
     *  创建方式二：实现Runnable接口
      */
    static class ByRunnable implements Runnable {

        private String name;

        public ByRunnable(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println(name + Thread.currentThread().getName());
        }
    }


    /**
     * 创建方式三：实现Callable接口
     */
    static  class ByCallable implements Callable<String>
    {
        private String name;

        public ByCallable(String name){
            this.name = name;
        }
        @Override
        public String call() throws Exception {
            System.out.println(name + Thread.currentThread().getName());
            return name;
        }
    }

    /**
     *  4 通过线程池创建线程
     */
    public static void createByPool(){

        /**
         * AbortPolicy：这是默认的策略。当线程池无法接受新任务时，
         *      会抛出RejectedExecutionException异常并中止任务的执行。调用者需要处理这个异常。
         * DiscardPolicy：这种策略会静默丢弃任务，不会抛出任何异常，也不会做其他处理。
         * DiscardOldestPolicy：此策略会丢弃队列中最旧的任务，以便为新被拒绝的任务腾出空间。
         *          被丢弃的任务不会被执行，而且也不会有任何通知。
         * CallerRunsPolicy：在这种策略下，如果线程池不能再接收新任务，
         *      那么提交任务的调用线程将自行执行该任务。
         */
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2,3,0, TimeUnit.SECONDS
                , new LinkedBlockingDeque<Runnable>(3), Executors.defaultThreadFactory()
                ,new ThreadPoolExecutor.CallerRunsPolicy());

            poolExecutor.submit(()->{
                System.out.println("4: created by poolExecutor " + Thread.currentThread().getName());
            });
            poolExecutor.shutdown();
    }

    /**
     *  5 create by CompletableFuture
     */
    public static void createByCompletableFuture(){
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->{
            System.out.println("5: created by completableFuture " + Thread.currentThread().getName());
            return "hello CompletableFuture";
        });

        completableFuture.thenAccept(System.out::println);
    }

    /**
     *  6 created by ThreadGroup
     */
    public static void createByThreadGroup(){
        ThreadGroup threadGroup = new ThreadGroup("groupName");
        new Thread(threadGroup,()->{
            System.out.println("6: created by threadGroup " + Thread.currentThread().getName());
        }, "create by threadGroup T1").start();
        new Thread(threadGroup,()->{
            System.out.println("6: created by threadGroup " + Thread.currentThread().getName());
        }, "create by threadGroup T2").start();
    }

    /**
     *  7 create by FutureTask
     */
    public static void createByFutureTask(){
        FutureTask<String> futureTask = new FutureTask<>(()-> {
            System.out.println("7: created by futureTask " + Thread.currentThread().getName());
            return "hello FutureTask";
        });

        new Thread(futureTask).start();

    }



    /**
     *  8 created by Timer
     */
    public static void createByTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("8: created by Timer " + Thread.currentThread().getName());
            }
        }, 0, 1000);
    }

    /**
     *  9 create by ForkJoinPool
     */
    public static void createByForkJoin(){
        ForkJoinPool join = new ForkJoinPool();
        join.execute(()->{
            System.out.println("9: created by ForkJoinPool " + Thread.currentThread().getName());
        });

    }

    /**
     *  10 create by parallelStream
     */
    public static void createByParallelStream(){
        List<String> list = Arrays.asList("10A", "10B");
        list.parallelStream().forEach(System.out::println);

    }


    /**
     *
     * @param args
     */


    public static void main(String[] args) {

        try {

            ByThread byThread1 = new ByThread("1: byThread ");
            byThread1.start();
            byThread1.join();

            Thread byRunnable = new Thread(new ByRunnable("2: byRunnable "));
            byRunnable.start();
            byRunnable.join();

            FutureTask<String> futureTask = new FutureTask<>(new ByCallable("3: byCallable "));
            Thread byCallable = new Thread(futureTask);
            byCallable.start();
            byCallable.join();

            createByPool();

            createByCompletableFuture();

            createByThreadGroup();

            createByFutureTask();

            createByTimer();

            createByForkJoin();

            createByParallelStream();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
