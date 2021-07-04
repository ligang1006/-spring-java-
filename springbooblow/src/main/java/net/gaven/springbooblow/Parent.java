package net.gaven.springbooblow;

/**
 * @author: lee
 * @create: 2021/6/29 9:51 上午
 **/
public abstract class Parent {
    public abstract void dosomething();

    public void say(){
        dosomething();
        System.out.println("www.chinoukin.com");
    }

    public static void main(String[] args) {
        new Parent() {
            @Override
            public void dosomething() {
                System.out.println("parent");
            }
        }.say();

    }
}
