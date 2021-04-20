package cn.lxw.io.nettty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/13 2:05 下午
 */
public class ChatNettyServerHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));

        Channel us = ctx.channel();

        channelGroup.forEach(channel -> {
            if (channel != us) {
                channel.writeAndFlush("[客户端]" + us.remoteAddress() + "发送了消息：" + buf.toString(CharsetUtil.UTF_8) + "\n");
            }else{
                ctx.writeAndFlush("自己发送了消息：" + buf.toString(CharsetUtil.UTF_8) + "\n");
            }
        });
    }


//    /**
//     * 数据读取完毕处理方法
//     * @param ctx
//     * @throws Exception
//     */
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ByteBuf buf = Unpooled.copiedBuffer("HelloClient", CharsetUtil.UTF_8);
//        ctx.writeAndFlush(buf);
//    }

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
