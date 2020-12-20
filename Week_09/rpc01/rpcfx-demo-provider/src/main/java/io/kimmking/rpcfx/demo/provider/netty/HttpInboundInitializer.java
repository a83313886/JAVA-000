package io.kimmking.rpcfx.demo.provider.netty;

import io.kimmking.rpcfx.server.RpcfxInvoker;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.stereotype.Component;

@Component
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
	
	private final RpcfxInvoker invoker;

	public HttpInboundInitializer(RpcfxInvoker invoker) {
		this.invoker = invoker;
	}
	
	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}
 		//p.addLast(new HttpRequestDecoder());
		//p.addLast(new HttpResponseEncoder());
		p.addLast(new HttpServerCodec());
		//p.addLast(new HttpServerExpectContinueHandler());
		p.addLast(new HttpObjectAggregator(1024 * 1024));

		// 每个channel都使用自己的handler
		// HttpInboundHandler 不能使用单例，否则报错：is not a @Sharable handler, so can't be added or removed multiple times
		p.addLast(new HttpInboundHandler(invoker));
	}
}
