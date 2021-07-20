package chapter1;

/**
 * 死锁问题
 * <p>
 * 两个线程1和2（两个人）
 * 1需要 A资源 同时锁住B资源
 * 2需要 B资源 同时锁住A资源
 * 形成一个环
 * <p>
 * 注意：⚠️这里不加休眠，可能不会有问题，执行速度非常快
 *
 * @author: lee
 * @create: 2021/7/20 6:40 下午
 **/
public class DeadLockDemo {
    private static final Object A = new Object();
    private static final Object B = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            //尝试获取资源A
            synchronized (A) {
                System.out.println("我需要资源A");
                try {
                    //这里不加休眠，可能不会有问题
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //尝试获取资源B
                synchronized (B) {
                    System.out.println("我需要资源B");
                }
            }
        }).start();

        new Thread(() -> {
            System.out.println("我需要资源B");
            //尝试获取资源B
            synchronized (B) {
                try {
                    //这里不加休眠，可能不会有问题
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //尝试获取资源A
                synchronized (A) {
                    System.out.println("我需要资源A");
                }
            }
        }).start();

    }
}
