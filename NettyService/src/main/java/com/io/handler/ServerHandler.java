package com.io.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/24 15:01
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel inComing = ctx.channel();
        System.out.println("[Server] - " + inComing.remoteAddress() + " 加入 ！");
        ctx.channel().writeAndFlush(inComing.remoteAddress() + " 连接成功 ！\n");
        channels.add(ctx.channel());
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel inComing = ctx.channel();
        System.out.println("[Server] - " + inComing.remoteAddress() + " 离开 ！");
        channels.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel inComing = ctx.channel();
        for (Channel channel : channels) {
            if (channel != inComing) {
                channel.writeAndFlush("[" + inComing.remoteAddress() + "]" + msg + "\n");
            } else {
                channel.writeAndFlush("[you] - " + msg + "\n");
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel inComing = ctx.channel();
        System.out.println("[Server] - " + inComing.remoteAddress() + " 在线 ！");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel inComing = ctx.channel();
        System.out.println("[Server] - " + inComing.remoteAddress() + " 掉线 ！");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel inComing = ctx.channel();
        System.out.println("[Server] - " + inComing.remoteAddress() + " 异常 ！");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
