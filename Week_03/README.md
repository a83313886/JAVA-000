# 学习笔记

1. netty那块之前没有用过，各种组件第一次接触，每个组件干什么的，怎么在IO模型里发挥作用，这些都要多熟悉
2. 我们为什么用netty而不是tomcat来处理HTTP？tomcat不是实现了servlet规范吗？

## 疑问
### 1、常说volatile修饰的变量适用于一写多读的场景，具体是什么场景？
如果存在一个线程写入，但是多个读线程都存在类似下面的逻辑：
```java
if(volatile a == constant b) {
    // then do something
    doTask()
}
```
执行判断那行语句时虽然volatile变量a满足条件，但是可能到执行`doTask()`时由于a已经被修改导致不满足条件，从而可能执行错误的操作。
因此问题里说的多读场景，还是要看具体的任务或者业务规则要求，例如是否要求在执行操作时变量不可变。

下面是一个反面例子，用于说明仅通过volatile关键字不能保证涉及多个变量的业务规则的线程安全：
```java
public class NumberRange {  
    private volatile int lower;
    private volatile int upper;  
  
    public int getLower() { return lower; }  
    public int getUpper() { return upper; }  
  
    public void setLower(int value) {   
        if (value > upper)   
            throw new IllegalArgumentException(...);  
        lower = value;  
    }  
  
    public void setUpper(int value) {   
        if (value < lower)   
            throw new IllegalArgumentException(...);  
        upper = value;  
    }  
}
```
如果凑巧两个线程在同一时间使用不一致的值执行 setLower 和 setUpper，则会使范围处于不一致的状态。例如，如果初始状态是(0, 5)，同一时间内，线程 A 调用setLower(4) 并且线程 B 调用setUpper(3)，显然这两个操作交叉存入的值是不符合条件的，那么两个线程都会通过用于保护不变式的检查，使得最后的范围值是(4, 3) —— 一个无效值。