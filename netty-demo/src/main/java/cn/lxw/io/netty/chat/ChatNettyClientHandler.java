package cn.lxw.io.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Random;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/13 2:23 下午
 */
public class ChatNettyClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 添加心跳包任务
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        Random random = new Random();
        Thread thread = new Thread(() ->{
            while(channel.isActive()){
                int num = random.nextInt(10);
                try {
                    Thread.sleep(num * 1000);
                    channel.writeAndFlush("heatbeat packet");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

    /**
     * 断线重连
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive:断线重连");
        ChatNettyClient.connect();
    }


    /**
     * 当通道有读取事件时触发，即服务器发送数据给客户端
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("收到消息:" + msg);

        if(msg!=null && msg.equals("idle close")){
            System.out.println("服务点关闭连接，客户端也关闭");
            ctx.channel().closeFuture();
        }
    }

    /**
     * 处理异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
