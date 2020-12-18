package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.api.RpcfxResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.ConcurrentHashMap;

public class DemoResolver implements RpcfxResolver, ApplicationContextAware {

    private final ConcurrentHashMap<String, Object> registry = new ConcurrentHashMap<>(5);

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object resolve(String serviceClass) {
        try {
            return this.applicationContext.getBean(Class.forName(serviceClass));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("could not find service by class[" + serviceClass + "]");
        }
    }
}
