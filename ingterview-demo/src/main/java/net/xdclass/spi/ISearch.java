package net.xdclass.spi;

import java.util.List;

/**
 * @author: lee
 * @create: 2021/5/26 2:26 下午
 **/
public interface ISearch {
    /**
     * 查询Doc文件
     *
     * @param keyword
     * @return
     */
    public List<String> searchDoc(String keyword);
}
