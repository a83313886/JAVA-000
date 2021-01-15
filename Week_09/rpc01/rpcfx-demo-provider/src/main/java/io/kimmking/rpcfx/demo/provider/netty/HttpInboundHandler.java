package io.kimmking.rpcfx.demo.provider.netty;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.kimmking.rpcfx.server.RpcfxInvoker;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderValues.APPLICATION_JSON;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final RpcfxInvoker invoker;
    
    public HttpInboundHandler(RpcfxInvoker invoker) {
        this.invoker = invoker;
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            doHandle(fullRequest, ctx);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

   private void doHandle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
       FullHttpResponse response = null;
       try {
           // request反序列化 => process => response序列化
           String requestBody = fullRequest.content().toString(StandardCharsets.UTF_8);
           RpcfxRequest rpcfxRequest = JSON.parseObject(requestBody, RpcfxRequest.class);
           RpcfxResponse rpcfxResponse = invoker.invoke(rpcfxRequest);
           String s = JSON.toJSONString(rpcfxResponse);
           response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(s.getBytes(StandardCharsets.UTF_8)));

           response.headers()
                   .set(CONTENT_TYPE, APPLICATION_JSON)
                   .setInt(CONTENT_LENGTH, response.content().readableBytes());
       } catch (Exception e) {
           logger.error("处理测试接口出错", e);
           response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
       } finally {
           if (fullRequest != null) {
               if (!HttpUtil.isKeepAlive(fullRequest)) {
                   ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                   // response.headers().set(CONNECTION, CLOSE);
               } else {
                   response.headers().set(CONNECTION, KEEP_ALIVE);
                   ctx.write(response);
               }
           }
       }
   }

   @Override
   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
       cause.printStackTrace();
       ctx.close();
   }

}
