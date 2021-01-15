## 搭建3个节点的Kafaka集群，测试功能和性能
1. 搭建集群的配置文件见`Week_13\kafka\cluster-config`
2. 相关测试命令
```shell script
>bin\windows\kafka-topics.bat --create --zookeeper 127.0.0.1:2181 --replication-factor 3 --partitions 3 --topic quickstart-events  
>bin\windows\kafka-producer-perf-test.bat --topic quickstart-events --num-records 1000000 --throughput 100000 --record-size 1000 --producer-props bootstrap.servers=localhost:9092,localhost:9093,localhost:9094
>bin\windows\kafka-consumer-perf-test.bat  --bootstrap-server localhost:9092,localhost:9093,localhost:9094  --topic quickstart-events --messages 1000000 --group c2 --fetch-size 20000
```
## 使用spring-kafka连接kafka集群
项目见`Week_13\kafka\demo`，按照配置文件启动集群后, 执行命令创建Topic，运行`DemoApplication`
* MessageSender，负责发送消息。发送时通过休眠随机的毫秒数来模拟实际情况
* MessageReceiver， 负责接收消息。