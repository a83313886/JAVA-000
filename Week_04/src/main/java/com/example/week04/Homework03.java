package com.example.week04;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class Homework03 {
    private static volatile Integer result = null;

    public static void main(String[] args) {
        // method1();
        // method2();
        // method3();
        // method4();
        // method5();
        // method6();
        // method7();
        // method8();
        // method9();
        // method10();
        method11();
    }

    /**
     * 基于原始线程执行，轮询判断是否执行完毕
     */
    private static void method1() {
        long start = System.currentTimeMillis();
        new Thread(() -> result = sum()).start();

        while (result == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // int result = sum(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        // 然后退出main线程
    }

    /**
     * 基于线程池执行，future阻塞等待获取执行结果
     */
    private static void method2() {

        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> sumFuture = executorService.submit(Homework03::sum);

        Integer result = null;
        try {
            result = sumFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

        executorService.shutdown();
        // 然后退出main线程
    }

    /**
     * 原生线程，wait/notify实现通知
     */
    private static void method3() {

        long start = System.currentTimeMillis();

        final Object lock = new Object();

        new Thread(() -> {
            result = sum();
            synchronized (lock) {
                lock.notifyAll();
            }
        }).start();

        while (result == null) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 原生线程，JOIN实现
     */
    private static void method4() {

        long start = System.currentTimeMillis();

        Thread thread = new Thread(() -> result = sum());

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + result);

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");

    }

    /**
     * 原生线程，ReentrantLock实现
     */
    private static void method5() {

        long start = System.currentTimeMillis();

        ReentrantLock lock = new ReentrantLock(true);

        Thread thread = new Thread(() -> {
            lock.lock();
            result = sum();
            lock.unlock();
        });

        lock.lock();

        thread.start();

        lock.unlock();

        // 自旋等待工作的线程获得锁，工作的线程获得锁后自动停止自旋
        while (!lock.isLocked()) {
        }
        lock.lock();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 原生线程，interrupt实现
     */
    private static void method6() {

        long start = System.currentTimeMillis();
        Thread mainThread = Thread.currentThread();

        Thread thread = new Thread(() -> {
            result = sum();
            mainThread.interrupt();
        });

        thread.start();

        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            // 被唤醒说明活干完了，直接往下处理
        }

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 原生线程，LockSupport实现
     */
    private static void method7() {

        long start = System.currentTimeMillis();
        Thread mainThread = Thread.currentThread();

        Thread thread = new Thread(() -> {
            result = sum();
            LockSupport.unpark(mainThread);
        });

        thread.start();
        LockSupport.park();

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 原生线程，CountDownLatch实现
     */
    private static void method8() {

        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread thread = new Thread(() -> {
            result = sum();
            countDownLatch.countDown();
        });
        thread.start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 原生线程，FutureTask实现
     */
    private static void method9() {

        long start = System.currentTimeMillis();

        FutureTask<Integer> futureTask = new FutureTask<>(Homework03::sum);

        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            result = futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }


    /**
     * CompletableFuture实现
     */
    private static void method10() {

        long start = System.currentTimeMillis();

        Integer result = CompletableFuture.supplyAsync(Homework03::sum).join();

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 原生线程，CyclicBarrier实现
     */
    private static void method11() {

        long start = System.currentTimeMillis();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        Thread thread = new Thread(() -> {
            result = sum();
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}
