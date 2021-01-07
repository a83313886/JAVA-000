package io.kimmking.cache.counter;

import io.kimmking.cache.pool.PooledJedis;
import redis.clients.jedis.Jedis;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 做分布式库存扣减的计数器
 */
public class SimpleDistributedCounter {
    private final String COUNTER_KEY;

    public SimpleDistributedCounter(long stockNum) {
        COUNTER_KEY = "counter::" + System.currentTimeMillis();
        System.out.println("COUNTER_KEY IS " + COUNTER_KEY);
        PooledJedis.getJedis().set(COUNTER_KEY, String.valueOf(stockNum));
    }

    public long incr() {
        return PooledJedis.getJedis().incr(COUNTER_KEY);
    }

    public long incrBy(long increment) {
        return PooledJedis.getJedis().incrBy(COUNTER_KEY, increment);
    }

    /**
     * 减少库存，返回当前库存
     * @return
     */
    public long decr() {
        Jedis jedis = PooledJedis.getJedis();
        // long currentCount = Long.parseLong(jedis.get(COUNTER_KEY));
        Long count = jedis.decr(COUNTER_KEY);
        if (count < 0) {
            jedis.incr(COUNTER_KEY);
            count = 0L;
            // throw new IllegalStateException("已经没有可以减少的库存");
        }
        jedis.close();
        return count;
    }

    public long decrBy(long increment) {
        Jedis jedis = PooledJedis.getJedis();
        Long count = jedis.decrBy(COUNTER_KEY, increment);
        if (count < 0) {
            jedis.incrBy(COUNTER_KEY, increment);
            // count = 0L;
            // throw new IllegalStateException("已经没有可以减少的库存");
        }
        jedis.close();
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int stockNum = 1000000;
        SimpleDistributedCounter counter = new SimpleDistributedCounter(stockNum);
        CopyOnWriteArrayList<Long> results = new CopyOnWriteArrayList<>();
        IntStream.rangeClosed(1, 10)
                .forEach(i -> executorService.submit(() -> {
                    while (true) {
                        try {
                            long decrResult = counter.decrBy(100);
                            // 扣减成负数，说明卖光了。因为decr命令是无脑减，不支持判断，所以只能等减超了才知道库存减完。为了让redis的数值保证为0，还要另外再加回去
                            if (decrResult < 0) {
                                System.out.println("全部卖光了！");
                                return;
                            }
                            results.add(decrResult);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // System.out.println("decrResult:" + decrResult);
                        /*try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                    }
                }));
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);

        // results.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
        System.out.println("size is:" + results.size());
        PooledJedis.close();
    }
}
