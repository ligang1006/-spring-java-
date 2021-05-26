package net.xdclass.spi.impl;

import net.xdclass.spi.ISearch;

import java.util.List;

/**
 * @author: lee
 * @create: 2021/5/26 2:28 下午
 **/
public class DatabaseSearch implements ISearch {
    @Override
    public List<String> searchDoc(String keyword) {
        System.out.println("数据搜索 "+keyword);
        return null;
    }
}
