package cn.lxw.io.netty.entity;

/**
 * @author xingweilin@clubfactory.com
 * @date 2021/4/15 5:52 下午
 */
public class NettyTest {

    private String name;

    private Integer uid;

    public NettyTest(){

    }

    public NettyTest(Integer uid, String name){
        this.uid = uid;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "NettyTest{" +
                "name='" + name + '\'' +
                ", uid=" + uid +
                '}';
    }
}
