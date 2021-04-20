package cn.lxw.io.netty.memory;

import java.nio.ByteBuffer;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/19 11:06 上午
 */
public class DirectTest {

    public static void main(String[] args) {
        ByteBuffer bb = ByteBuffer.allocateDirect(1024);
    }
}
