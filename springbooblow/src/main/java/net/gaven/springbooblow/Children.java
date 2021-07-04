package net.gaven.springbooblow;

/**
 * @author: lee
 * @create: 2021/6/29 9:57 上午
 **/
public class Children extends Parent {
    @Override
    public void dosomething() {
        System.out.println("张三来啦");
    }
    public static void main(String[] args){
        Children c = new Children();
        c.say();
    }
}
