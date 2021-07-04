package net.gaven.springbooblow;

/**
 * @author: lee
 * @create: 2021/6/29 10:05 上午
 **/
public class Son extends Parent{
    @Override
    public void dosomething() {
        System.out.println("son");
    }
    public static void main(String[] args){
        Son c = new Son();
        c.say();
    }
}
