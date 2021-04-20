package cn.lxw.io.netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/13 2:13 下午
 */
public class ChatNettyClient {

    private static Integer retrytimes = 3;

    private static Bootstrap bootstrap = new Bootstrap();

    public static void main(String[] args) {

        //客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());

                            pipeline.addLast(new ChatNettyClientHandler());
                        }
                    });
            System.out.println("netty client start");
            connect();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void connect() {
        try {
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();

            Channel channel = channelFuture.channel();
            System.out.println("================" + channel.remoteAddress() + "==============");
            Scanner scanner = new Scanner(System.in);
            do {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg);
            } while (true);
        } catch (Exception e) {
            if (retrytimes > 0) {
                System.out.println("尝试重连。。。。");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                retrytimes -= 1;
                connect();
            } else {
                e.printStackTrace();
                System.out.println("重连次数超限");
            }
        }
    }
}
