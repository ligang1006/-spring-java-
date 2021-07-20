package chapter1;

import java.util.concurrent.CountDownLatch;

/**
 * 线程安全
 *
 * @author: lee
 * @create: 2021/7/20 7:39 下午
 **/
public class UnsafeThread1 {
    private static Integer num = 0;
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("线程A开始了,要给num增加500分");
            for (int i = 0; i < 10; i++) {
                inCreate();
            }
        }).start();
//        Thread.sleep(3000);
        System.out.println("最终获取的数量为" + num);
    }
    private static void inCreate() {
        num++;
    }
}
