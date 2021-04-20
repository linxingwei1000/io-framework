package cn.lxw.io.jdk.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/12 4:21 下午
 */
public class ClientDemo {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();

        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9000)).get();
        socketChannel.write(ByteBuffer.wrap("helloServer".getBytes()));

        ByteBuffer buffer = ByteBuffer.allocate(512);

        Integer len = socketChannel.read(buffer).get();
        if (len != -1) {
            System.out.println("客户端接收消息：" + new String(buffer.array(), 0, len));
        }


    }
}
