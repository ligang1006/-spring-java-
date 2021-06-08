package net.gaven.springdemo.iocmyself.readme.springbean;

/**
 * @author: lee
 * @create: 2021/6/8 7:00 下午
 **/
public class C {
    B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public C(B b) {
        this.b = b;
    }
}
