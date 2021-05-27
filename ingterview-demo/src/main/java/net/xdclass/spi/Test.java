package net.xdclass.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author: lee
 * @create: 2021/5/26 2:30 下午
 **/
public class Test {
    public static void main(String[] args) {
        //加载接口
        ServiceLoader<ISearch> s = ServiceLoader.load(ISearch.class);
        Iterator<ISearch> iterator = s.iterator();
        while (iterator.hasNext()) {
            ISearch search =  iterator.next();
            search.searchDoc("hello world");
        }
    }
}
