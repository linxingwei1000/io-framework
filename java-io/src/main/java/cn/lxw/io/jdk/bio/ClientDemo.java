package cn.lxw.io.jdk.bio;

import java.io.IOException;
import java.net.Socket;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/12 3:34 下午
 */
public class ClientDemo {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 9000);

        socket.getOutputStream().write("helloServer".getBytes());
        socket.getOutputStream().flush();

        System.out.println("向服务端发送数据结束");

        byte[] bytes = new byte[1024];

        int len = socket.getInputStream().read(bytes);
        System.out.println("接收到服务端数据：" + new String(bytes, 0, len));
        socket.close();
    }
}
