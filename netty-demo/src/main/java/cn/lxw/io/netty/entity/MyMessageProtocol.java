package cn.lxw.io.netty.entity;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/16 10:58 上午
 */
public class MyMessageProtocol {

    private int len;

    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
