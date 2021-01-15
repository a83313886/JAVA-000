package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.demo.api.OrderService;
import io.kimmking.rpcfx.demo.api.UserService;
import io.kimmking.rpcfx.demo.provider.netty.HttpInboundServer;
import io.kimmking.rpcfx.server.RpcfxInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RpcfxServerApplication {

	public static void main(String[] args) {
		// SpringApplication.run(RpcfxServerApplication.class, args);
		runWithNetty(args);
	}

	public static void runWithNetty(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(RpcfxServerApplication.class, args);
		HttpInboundServer server = context.getBean(HttpInboundServer.class);
		try {
			server.run();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	// @Autowired
	// RpcfxInvoker invoker;

/*	@PostMapping("/")
	public RpcfxResponse invoke(@RequestBody RpcfxRequest request) {
		return invoker.invoke(request);
	}*/

	@Bean
	public RpcfxInvoker createInvoker(@Autowired RpcfxResolver resolver){
		return new RpcfxInvoker(resolver);
	}

	@Bean
	public RpcfxResolver createResolver(){
		return new DemoResolver();
	}

	@Bean
	public UserService createUserService(){
		return new UserServiceImpl();
	}

	@Bean
	public OrderService createOrderService(){
		return new OrderServiceImpl();
	}

}
