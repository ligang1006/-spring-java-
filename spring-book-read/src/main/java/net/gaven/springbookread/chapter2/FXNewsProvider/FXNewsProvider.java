package net.gaven.springbookread.chapter2.FXNewsProvider;

/**
 * @author: lee
 * @create: 2021/6/8 2:05 下午
 **/
public class FXNewsProvider {
    private IFXNewsListener newsListener;
    private IFXNewsPersister newPersistener;

    public void getAndPersistNews() {
        String[] newsIds = newsListener.getAvailableNewsIds();
        if (ArrayUtils.isEmpty(newsIds)) {
            return;
        }
        for (String newsId : newsIds) {
            FXNewsBean newsBean = newsListener.getNewsByPK(newsId);

            newPersistener.persistNews(newsBean);
            newsListener.postProcessIfNecessary(newsId);

        }
    }
    public FXNewsProvider()
    {
        newsListener = new DowJonesNewsListener();
        newPersistener = new DowJonesNewsPersister();
    }
}
