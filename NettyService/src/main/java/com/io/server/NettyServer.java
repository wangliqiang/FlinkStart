package com.io.server;

import com.io.handler.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description TODO
 * @Author wangliqiang
 * @Date 2019/6/24 9:27
 */
public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        EventLoopGroup boos = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        serverBootstrap.group(boos,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer())
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true);
        System.out.println("[Server] - Started");

        ChannelFuture channelFuture = serverBootstrap.bind(8000);

        try {
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
