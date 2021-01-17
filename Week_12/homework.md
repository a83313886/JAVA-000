# 配置redis的主从复制，sentinel高可用，Cluster集群： 
## 1）config配置文件
1. 主从复制配置文件见`master-replica`目录下的两个文件。使用的redis版本是6.0.3
2. Sentinel高可用配置文件见`sentinel`目录下的sentinel0和sentinel1文件
3. Cluster配置参见`cluster`目录下的redis6379～redis6384文件。搭建的是3主3从的集群，使用脚本分配槽位
## 2）启动和操作、验证集群下数据读写的命令步骤。
* 集群下执行读写，使用redis-cli命令行来操作，如果目标key所属的槽位不是当前连接节点，会显示`MOVED 15627 127.0.0.1:6381`类似的提示信息，告知要去另外一个节点进行操作
