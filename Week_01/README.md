## 学习心得
1. GC算法有点复杂，尤其是CMS和G1后面还分了好多个步骤，每个步骤做的内容都不容易理解
2. JVM的内存布局还有疑惑，如堆外内存、DirectMemory这些怎么理解

## 做题

### 题目一：自定义classloader
参见`HelloClassloader`类

### 疑问
1. 为什么`findClass()`最后一定要调用`Classloader`的`defindClass`方法返回？

### 题目二：画图解释参数关系
画的图见`vmArgument.png`文件