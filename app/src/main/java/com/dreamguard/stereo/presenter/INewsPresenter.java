package com.dreamguard.stereo.presenter;

/**
 * Created by hailin on 2017/07/10.
 */
public interface INewsPresenter extends BasePresenter{
    void getNews(int page);
    void getNewsFromCache(int page);
}
