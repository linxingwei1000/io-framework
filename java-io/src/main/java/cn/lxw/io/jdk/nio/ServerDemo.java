package cn.lxw.io.jdk.nio;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/12 3:47 下午
 */
public class ServerDemo {

    static List<SocketChannel> channelList = Lists.newArrayList();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9000));

        //设置非阻塞
        serverSocket.configureBlocking(false);
        System.out.println("服务启动成功");

        while (true) {
            SocketChannel socketChannel = serverSocket.accept();
            if (socketChannel != null) {
                //设置socketChannel为非阻塞
                socketChannel.configureBlocking(false);

                channelList.add(socketChannel);
            }

            Iterator<SocketChannel> ite = channelList.iterator();
            while (ite.hasNext()) {
                SocketChannel sc = ite.next();

                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                int len = sc.read(byteBuffer);

                if (len > 0) {
                    System.out.println("接收到消息：" + new String(byteBuffer.array()));
                } else if (len == -1) {
                    ite.remove();
                    System.out.println("客户端断开连接");
                }
            }
        }
    }
}
