## 题目一：实现订单表的分库分表和相关CRUD操作
1. 修改原建表脚本文件，把order表添加前缀`t_`，避免直接使用order这个关键字作为表名，新的建表脚本见`ddl_t_order.sql`
2. 修改订单表建表脚本，删除主键id自增的设置
3. 启动MySql实例并搭建ShardingSphere Proxy服务，根据官方说明文档操作：https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-proxy
4. 主要修改了proxy中的两个配置文件`config-sharding.yaml`和`server.yaml`，配置文件已传到`shardingSphere-proxy-config`目录
5. 调用`start.bat`启动proxy服务，利用mysql命令行连接到proxy上。登陆命令：`bin\mysql -u root -h 127.0.0.1 -P 3307 -p`
6. 通过命令行执行建表脚本，proxy自动按照分库分表规则创建了表
7. 执行`insert into `db`.`t_order` (`user_id`,`status`,`pay_status`,`price`,`create_by`,`update_by`) VALUES (0,0,0,23200874,'lusjd','lusjd');`, proxy自动生成id主键并插入某个库的某个表
8. 修改price字段的值再插入数据，proxy会根据分表规则插入另外一个表
9. `select * from t_order`，proxy广播查询
10. `select * from t_order where id = 543730395741925377 and price = 23200874`，查询带分库和分表的分片键，proxy直接定位到某张表做查询
11. `select * from t_order where id > 543730395741925377 and price = 23200874`, 查询报错并提示`allow-range-query-with-inline-sharding`属性是false，不支持范围查询。修改`server.yaml`，添加该属性，重启proxy后发现无效。查阅资料后，把这个属性添加到`config-sharding.yaml`的`shardingAlgorithms`的每个分片算法属性配置中，重启proxy后可以成功查询。语句被广播到两个数据源的某张表
12. `select * from t_order where id = 543730395741925377 and price < 23200874`, 查询发现语句定位到ds_1，但是广播到这个数据库所有的分片表
13. `select * from t_order where id > 543730395741925377 and price < 23200874`, 查询发现语句走了全广播查询，查询了所有数据库和表
14. `update t_order set user_id = 20 where id > 543730395741925377 and price < 23200874`, 查询发现语句走了全广播更新，特征跟上面的查询一致
15. `delete from t_order where id = 543730395741925377 and price = 23200874`, proxy直接定位到某张表删除数据

## 题目二：基于Hmily TCC或ShardingSphere的Atomikos XA实现分布式事务应用demo
参见`week08-demo`项目，涉及两个MYSQL实例，第一个实例有表`ORDER_DETAIL`，第二个实例有表`PRODUCT`。
同时往两个表插入数据，引入ShardingSphere-jdbc的XA事务来提供保障。
* 带XA的事务方法，见`com.example.week08demo.repo.DemoRepository#insertWithXA`
* 不带XA的事务方法，见`com.example.week08demo.repo.DemoRepository#insertWithoutXA`
* 规则配置见`application-shardingDatabases.properties`文件
* 本次作业意外发现ShardingSphere-jdbc可以做垂直的分库