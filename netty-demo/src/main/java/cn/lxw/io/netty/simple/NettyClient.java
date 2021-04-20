package cn.lxw.io.netty.simple;

import cn.lxw.io.netty.handler.ChangeLengthContentEncoder;
import cn.lxw.io.netty.handler.ProtostuffEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/13 2:13 下午
 */
public class NettyClient {

    public static void main(String[] args) {

        //客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

//                            //测试protistuffEncoder 编码
//                            ch.pipeline().addLast("encoder", new ProtostuffEncoder());

                            //粘包分包处理handler
                            ch.pipeline().addLast("encoder", new ChangeLengthContentEncoder());
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("netty client start");

            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 9000).sync();

            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
