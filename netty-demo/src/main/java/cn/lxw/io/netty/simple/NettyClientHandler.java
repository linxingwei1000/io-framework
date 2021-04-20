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
 * @date 2021/4/13 2:23 下午
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    /**
     * 当客户端连接服务器完成触发该方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //ByteBuf buf = Unpooled.copiedBuffer("HelloServer", CharsetUtil.UTF_8);
        //NettyTest nettyTest = new NettyTest(100, "lxw");

        MyMessageProtocol myMessageProtocol = new MyMessageProtocol();
        byte[] bytes = "HelloServer".getBytes();
        myMessageProtocol.setContent(bytes);
        myMessageProtocol.setLen(bytes.length);
        ctx.writeAndFlush(myMessageProtocol);
    }

    /**
     * 当通道有读取事件时触发，即服务器发送数据给客户端
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("收到服务端的消息:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务端的地址: " + ctx.channel().remoteAddress());
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
        cause.printStackTrace();
        ctx.close();
    }
}
