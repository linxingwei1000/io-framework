package cn.lxw.io.jdk.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/12 3:33 下午
 */
public class ServerDemo {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9000);

        while(true){
            System.out.println("等待连接。。。。。。");

            Socket socket = ss.accept();
            System.out.println("有客户端连接了。。。。。。");

            //handler(socket);

            //多线程模式
            new Thread(() -> {
                try {
                    handler(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

    private static void handler(Socket socket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备read。。。。");

        int read = socket.getInputStream().read(bytes);
        System.out.println("read完毕。。。。。。");

        if(read != -1){
            System.out.println("接收客户端请求数据：" + new String(bytes, 0, read));
        }

        socket.getOutputStream().write("helloClient".getBytes());
        socket.getOutputStream().flush();
    }
}
