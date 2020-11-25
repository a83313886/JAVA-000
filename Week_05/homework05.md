### 题目：写代码实现Spring Bean的装配
参见以下类型：
* XmlBasedApplication, 基于XML的配置
* JavaBasedApplication， 基于JavaConfig的配置
* AnnotationBasedApplication，基于注解的配置(基础Bean定义来自xml)
* GroovyBasedApplication， 基于groovy文件的配置
* MixStyleApplication， 混合风格，包括xml和javaConfig、annotation的配置

### 题目：编写starter
`JavaBeanAutoConfiguration`是编写的自动配置类，运行`DemoApplication`可以验证该starter自动配置了School实例，并且student的name是来自配置文件

### 题目：JDBC和连接池
* `BaseJdbcImpl`是原生接口实现
* `BaseJdbcImpl2`是添加了事务、PrepareStatement和批处理
* `BaseJdbcImpl3`是使用Hikari连接池
