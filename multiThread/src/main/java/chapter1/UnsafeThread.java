package chapter1;

import java.util.concurrent.CountDownLatch;

/**
 * 线程安全,这个横0
 *
 * @author: lee
 * @create: 2021/7/20 7:39 下午
 **/
public class UnsafeThread {
    private static int num = 0;

    private static CountDownLatch countDownLatch = new CountDownLatch(10);

    /**
     * 每次调用对num进行++操作
     */
    public static void inCreate() {
        num++;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inCreate();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //每个线程执行完成之后，调用countdownLatch
                countDownLatch.countDown();
            }).start();
        }
        while (true) {
            if (countDownLatch.getCount() == 0) {
                System.out.println(num);
                break;
            }
        }
    }

}
