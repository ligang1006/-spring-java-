package chapter1;

/**
 * 错误的方法，不能把inCreate方法加参数，否则每次都是从0获取
 * @author: lee
 * @create: 2021/7/20 8:24 下午
 **/
public class ErrorUnsafeThread {
    private static Integer num = 0;
    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            System.out.println("线程A开始了,要给num增加500分");
            for (int i = 0; i < 500; i++) {
                inCreate(num);
            }
        }).start();
        System.out.println("最终获取的数量为" + num);
    }
    private static void inCreate(Integer num) {
        num++;
    }
}
