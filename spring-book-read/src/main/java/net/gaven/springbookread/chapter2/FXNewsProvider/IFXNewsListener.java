package net.gaven.springbookread.chapter2.FXNewsProvider;

/**
 * @author: lee
 * @create: 2021/6/8 2:12 下午
 **/
public interface IFXNewsListener {
    String[] getAvailableNewsIds();

    FXNewsBean getNewsByPK(String newsId);

    void postProcessIfNecessary(String newsId);
}
