## 题目一：批量插入数据，比较性能
批量插入数据的存储过程脚本见文件`batch_insert_order.sql`，Java代码放在第五周的jdbc项目里。
### 结果：
* 存储过程方式，分批提交，100万数据插入耗时32秒
* 单线程 单条语句插入，20分钟插入20万数据，100万估计需要100分钟
* 单线程 preparestatement batch方式 100万数据耗时111秒
* 10个线程 preparestatement batch方式 100万数据耗时30秒
* 20个线程 preparestatement batch方式 100万数据耗时26秒, 跟10个线程比没快多少

* 存储过程方式，分批提交，1000万数据插入耗时334秒
* 单线程 preparestatement batch方式 1000万数据耗时1022秒
* 10个线程 preparestatement batch方式 1000万数据耗时300秒
### 结论
想要得到最高性能还是要靠多线程批量插入模式。存储过程也能得到不错的性能，但是并不是最快的

## 题目二：读写分离-动态切换数据源版本1.0
见week_07 下面的demo项目，基于JPA实现
### 手动切换版本
不同的dataSource配置到不同的repository，使用时选择对应的repository来访问不同的数据源。
主要类:
* `DataSourceConfig`，配置数据源
* `DB1JpaConfig`，配置了主库的repository
* `DB2JpaConfig`，配置了从库的repository
* `MainController`类，插入数据和查询数据分别使用不同的repository
### 注解、多个从库、从库负载均衡版本
主要类:
* `ReadOnly`, 用来标志为只读的注解
* `DynamicDataSource`，动态数据源实现，继承了`AbstractRoutingDataSource`，支持round robin轮询方式来切换注册的从库
* `DynamicDataSourceAspect`，AOP切面类，用于拦截`ReadOnly`注解并设置、清理线程的数据源标识对象
* `DataSourceContextHolder`，holder类，用于保存当前线程使用的数据源标识对象
* `DynamicDataSourceJpaConfig`，配置了动态数据源的repository
* `DynamicDataSourceController`类，插入数据和查询数据分别使用同一个repository，根据注解自动切换

#### 问题
从库是否可以实现配置时自动注册，不需要每个从库显示编写注册bean的方法？

## 题目三：读写分离-数据库框架版本2.0 
处理中。。。