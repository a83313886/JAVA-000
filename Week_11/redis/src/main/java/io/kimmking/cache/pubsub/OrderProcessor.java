package io.kimmking.cache.pubsub;

import io.kimmking.cache.pool.PooledJedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class OrderProcessor {
    private final String ORDER_CHANNEL;
    private Jedis jedis;

    public OrderProcessor(String order_channel) {
        ORDER_CHANNEL = order_channel == null ? "ORDER_CHANNEL" : order_channel;
    }

    public void placeOrder() {
        Jedis jedis = PooledJedis.getJedis();
        jedis.publish(ORDER_CHANNEL, "hello world");
        jedis.close();
    }

    public void beginProcessOrder() {
        Jedis jedis = PooledJedis.getJedis();
        this.jedis = jedis;
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("receive:" + message);
            }
        }, ORDER_CHANNEL);
    }

    public void stopProcess() {
        this.jedis.close();
    }

    public static void main(String[] args) throws InterruptedException {
        OrderProcessor op = new OrderProcessor(null);
        Thread a = new Thread(() -> op.beginProcessOrder());
        a.start();
        op.placeOrder();
        op.placeOrder();
        Thread.sleep(1999);
        System.exit(0);
        // op.stopProcess();
        // op.placeOrder();
        // System.out.println("====end===");
    }
}
