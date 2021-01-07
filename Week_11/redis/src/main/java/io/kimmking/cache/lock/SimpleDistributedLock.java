package io.kimmking.cache.lock;

import io.kimmking.cache.pool.PooledJedis;

import java.util.concurrent.CountDownLatch;

/**
 * simple distributed lock implemented by redis
 * <p>
 *     默认设置了5秒钟超时，锁超时了会被自动释放。需要考虑自动延时的机制；当前线程可重入的机制
 * </p>
 *
 * @author syc
 */
public class SimpleDistributedLock {
    // private final Jedis jedis = new Jedis("localhost", 6379);
    private final String LOCK_KEY;

    public SimpleDistributedLock(String lockKey) {
        if (lockKey == null) {
            LOCK_KEY = "lock:default";
        } else {
            LOCK_KEY = lockKey;
        }
    }

    /**
     * 尝试获取锁，返回获取结果
     * @return true-成功 false-失败
     */
    public boolean tryLock() {
        // set lock:codehole true ex 5 nx
        String setResult = PooledJedis.getJedis().set(LOCK_KEY, "1", "nx", "ex", 5);
        System.out.println(setResult);
        return "OK".equals(setResult);
    }

    /**
     * 锁定当前线程，每隔500毫秒尝试获取锁，直到获取成功
     * @throws InterruptedException
     */
    public void lock() throws InterruptedException {
        while (true) {
            String setResult = PooledJedis.getJedis().set(LOCK_KEY, "1", "nx", "ex", 5);
            if (!"OK".equals(setResult)) {
                Thread.sleep(500);
            } else {
                break;
            }
        }
    }

    public void unlock() {
        Long delResult = PooledJedis.getJedis().del(LOCK_KEY);
        // System.out.println(delResult);
        if (delResult != 1) {
            throw new IllegalStateException("must acquire a lock first");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        // 考虑换一个客户端，以便使用同一个锁对象
        SimpleDistributedLock lock = new SimpleDistributedLock(null);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        Runnable task = () -> {
            // 尝试获取锁，失败后直接返回
            /*if (!lock.tryLock()) {
                System.out.println("tryLock fail." + Thread.currentThread().getName());
                return;
            }*/
            // 尝试获取锁，阻塞等待直到获取成功或者锁超时被释放
            try {
                lock.lock();
                System.out.println("acquire lock success." + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // System.out.println("================");
            try {
                Thread.sleep(1000);
                System.out.println("process complete. " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

            countDownLatch.countDown();
        };

        Thread a = new Thread(task, "thread-A");
        Thread b = new Thread(task, "thread-B");
        a.start();
        b.start();

        countDownLatch.await();
        PooledJedis.close();
    }
}
