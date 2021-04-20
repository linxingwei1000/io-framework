package cn.lxw.io.netty.simple;

import cn.lxw.io.netty.entity.MyMessageProtocol;
import cn.lxw.io.netty.entity.NettyTest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/13 2:05 下午
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    /**
     * 读取客户端发送数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程" + Thread.currentThread().getName());
        //ByteBuf buf = (ByteBuf) msg;

        //NettyTest nettyTest = (NettyTest) msg;
        MyMessageProtocol myMessageProtocol = (MyMessageProtocol) msg;
        System.out.println("客户端发送消息是:" + new String(myMessageProtocol.getContent()));
    }


    /**
     * 数据读取完毕处理方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ByteBuf buf = Unpooled.copiedBuffer("HelloClient", CharsetUtil.UTF_8);
//        ctx.writeAndFlush(buf);
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
