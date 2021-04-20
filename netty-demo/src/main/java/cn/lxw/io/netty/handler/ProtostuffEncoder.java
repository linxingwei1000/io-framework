package cn.lxw.io.netty.handler;

import cn.lxw.io.netty.ProtostuffUtil;
import cn.lxw.io.netty.entity.NettyTest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/15 5:44 下午
 */
public class ProtostuffEncoder extends MessageToByteEncoder<NettyTest> {


    @Override
    protected void encode(ChannelHandlerContext ctx, NettyTest msg, ByteBuf out) throws Exception {
        out.writeBytes(ProtostuffUtil.serializer(msg));
    }
}
