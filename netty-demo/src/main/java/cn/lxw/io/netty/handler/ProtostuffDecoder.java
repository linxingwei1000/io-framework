package cn.lxw.io.netty.handler;

import cn.lxw.io.netty.ProtostuffUtil;
import cn.lxw.io.netty.entity.NettyTest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/15 5:44 下午
 */
public class ProtostuffDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readableBytes();

        byte[] context = new byte[length];
        in.readBytes(context);

        NettyTest user = ProtostuffUtil.deserializer(context, NettyTest.class);
        System.out.println("读取客户端数据：" + user);
    }
}
