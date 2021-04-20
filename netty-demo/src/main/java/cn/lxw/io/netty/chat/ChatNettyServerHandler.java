package cn.lxw.io.netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/13 2:05 下午
 */
public class ChatNettyServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int readIdleTimes = 0;

    /**
     * 客服端连接，发送消息
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[ 客户端 ]" + channel.remoteAddress() + " 上线了 " + sdf.format(new Date()) + "\n");
        channelGroup.add(channel);
        System.out.println("客户端：" + channel.remoteAddress() + "登录");
    }

    /**
     * 客户端断线，移除channel
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.remove(channel);
        System.out.println("客户端：" + channel.remoteAddress() + "下线");

        channelGroup.writeAndFlush("[ 客户端 ]" + channel.remoteAddress() + " 下线了 " + sdf.format(new Date()) + "\n");

        System.out.println("客户端个数：" + channelGroup.size());

    }

    /**
     * 读取客户端发送数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel us = ctx.channel();

        if (msg.equals("heatbeat packet")) {
            System.out.println("收到客户端心跳包：" + us.remoteAddress());
            us.writeAndFlush("receive ht ok");
            return;
        }

        System.out.println("客户端发送消息是:" + msg);
        channelGroup.forEach(channel -> {
            if (channel != us) {
                channel.writeAndFlush("[客户端]" + us.remoteAddress() + "发送了消息：" + msg + "\n");
            } else {
                ctx.writeAndFlush("自己发送了消息：" + msg + "\n");
            }
        });
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent event = (IdleStateEvent) evt;

        String eventType = null;
        switch (event.state()) {
            case READER_IDLE:
                eventType = "读空闲";
                readIdleTimes++;
                break;
            case WRITER_IDLE:
                eventType = "写空闲";
                break;
            case ALL_IDLE:
                eventType = "读写空闲";
                break;
        }
        System.out.println(ctx.channel().remoteAddress() + "超时事件:" + eventType);
        if (readIdleTimes > 3) {
            System.out.println(" [server]读空闲超过3次，关闭连接，释放更多资源");
            ctx.channel().writeAndFlush("idle close");
            ctx.channel().close();
        }
    }

    /**
     * 处理异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
