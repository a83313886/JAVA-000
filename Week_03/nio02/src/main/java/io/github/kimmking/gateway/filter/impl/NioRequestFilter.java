package io.github.kimmking.gateway.filter.impl;

import io.github.kimmking.gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class NioRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        if (fullRequest != null) {
            fullRequest.headers().add("nio", "GuoYaodong");
        }
    }
}
