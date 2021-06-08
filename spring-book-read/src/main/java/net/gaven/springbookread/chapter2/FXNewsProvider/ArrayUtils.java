package net.gaven.springbookread.chapter2.FXNewsProvider;

/**
 * @author: lee
 * @create: 2021/6/8 2:13 下午
 **/
public class ArrayUtils {
    public static Boolean isEmpty(String[] array) {
        if (array == null || array.length == 0) {
            return true;
        } else {
            return false;
        }
    }
}
