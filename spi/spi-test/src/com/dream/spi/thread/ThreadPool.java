package com.dream.spi.thread;


import java.util.concurrent.*;

/**
 * @author 思维穿梭
 */
public class ThreadPool {

    static class MyThread extends Thread {

        private String name;

        public MyThread(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println(name + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        // 创建一个固定大小的线程池，里面有2个线程
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new MyThread("fixed "));
        executorService.shutdown();
        // 创建一个可缓存的线程池，调用execute将重用以前构造的线程（如果线程可用）。如果现有线程
        // 没有可用的，则创建一个新线程并添加到池中。终止并从缓存中移除那些已有60秒钟
        // 未被使用的线程。
        ExecutorService executorService1 = Executors.newCachedThreadPool();
        executorService1.execute(new MyThread("cached "));
        executorService1.shutdown();
        // 创建一个单线程化的Executor。
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();
        executorService2.execute(new MyThread("single "));
        executorService2.shutdown();
        // 创建一个支持定时及周期性的任务执行的线程池，多数情况下可用来替代Timer类。
        ScheduledExecutorService executorService3 = Executors.newScheduledThreadPool(2);
        Runnable task = () -> System.out.println("Hello, newScheduledThreadPool!");
        executorService3.scheduleAtFixedRate(task, 1, 2, TimeUnit.SECONDS);
//        executorService3.shutdown();
        // 创建一个单线程化的Executor，所有提交的任务都将被序列化处理。
        ScheduledExecutorService  executorService4 = Executors.newSingleThreadScheduledExecutor();
        // 定义一个Runnable任务
        Runnable task1 = () -> System.out.println("Hello, newSingleThreadScheduledExecutor!");

        // 使用执行器调度任务，延迟1秒后执行，每隔2秒执行一次
        executorService4.scheduleAtFixedRate(task1, 1, 2, TimeUnit.SECONDS);
//        executorService4.shutdown();
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
            System.out.println("ThreadPoolExecutor " + Thread.currentThread().getName());
        });
        poolExecutor.shutdown();

    }
}
