package cn.lxw.io.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/16 10:59 上午
 */
public class ChangeLengthContentDecoder extends ByteToMessageDecoder {

    int length = 0;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        System.out.println(in);

        if (in.readableBytes() > 4) {
            if (length == 0) {
                length = in.readInt();
            }

            if (in.readableBytes() < length) {
                System.out.println("当前睡不够，继续等待。。。。");
                return;
            }

            byte[] content = new byte[length];
            if (in.readableBytes() >= length) {
                in.readBytes(content);
            }

            System.out.println(in);
            System.out.println("读到的消息：" + new String(content));
        }

        length = 0;
    }
}
