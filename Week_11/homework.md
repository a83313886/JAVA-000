# 基于Redis封装分布式数据操作
## 1）在Java中实现一个简单的分布式锁； 
代码见`SimpleDistributedLock`类，利用jedis客户端来连接redis。提供了tryLock、lock和unlock方法
## 2）在Java中实现一个分布式计数器，模拟减库存。 
代码见`SimpleDistributedCounter`类，用多线程来模拟分布式。每次减少的库存可以调整
## 5、基于Redis的PubSub实现订单异步处理
代码见`OrderProcessor`类