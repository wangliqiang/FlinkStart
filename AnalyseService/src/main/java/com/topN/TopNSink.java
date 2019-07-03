package com.topN;

import com.netty.ClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

/**
 * @Description Sink到netty服务端
 * @Author wangliqiang
 * @Date 2019/6/20 10:16
 */
public class TopNSink extends RichSinkFunction<String> {

    EventLoopGroup group;
    Channel channel;

    private static String IP = "127.0.0.1";
    private static int PORT = 8000;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        Bootstrap bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ClientInitializer());

        channel = bootstrap.connect(IP, PORT).channel();
    }

    @Override
    public void invoke(String value, Context context) {
        channel.writeAndFlush(value+"\n");
    }

    @Override
    public void close() throws Exception {
        super.close();
        group.shutdownGracefully();
    }

}
