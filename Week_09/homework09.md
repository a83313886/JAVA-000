## 作业一：改造自定义RPC的程序
### 将服务端写死查找接口实现类变成泛型和反射
见`DemoResolver`的resolve方法，已经改成根据className动态加载获取class，再重context中获取类的bean
### 尝试将客户端动态代理改成AOP/字节码生成，添加异常处理
见`Rpcfx.createByByteBuddy`，使用byteBuddy动态生成代理的类，提高性能。
新增了`RpcfxException`，出现异常时封装到Response中返回
### 尝试使用Netty+HTTP作为client端传输方式
见`RpcfxServerApplication`的`runWithNetty`方法，引入netty后，请求和响应中的JSON的序列化和反序列化需要自己处理，原来Spring Web会帮我们处理的。
客户端不涉及变更

## 作业二：结合dubbo+hmily，实现一个TCC外汇交易处理
processing...