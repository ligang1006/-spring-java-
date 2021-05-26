package net.xdclass.spi.impl;

import net.xdclass.spi.ISearch;

import java.util.List;

/**
 * @author: lee
 * @create: 2021/5/26 2:27 下午
 **/
public class FileSearch implements ISearch {
    @Override
    public List<String> searchDoc(String keyword) {
        System.out.println("文件搜索"+keyword);
        return null;
    }
}
