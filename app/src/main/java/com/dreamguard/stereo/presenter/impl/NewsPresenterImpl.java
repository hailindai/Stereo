package com.dreamguard.stereo.presenter.impl;

import android.content.Context;

import com.dreamguard.stereo.R;
import com.dreamguard.stereo.api.weixin.TxRequest;
import com.dreamguard.stereo.bean.weixin.TxWeixinResponse;
import com.dreamguard.stereo.presenter.INewsPresenter;
import com.dreamguard.stereo.ui.iview.INewsFragment;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hailin on 17-7-10.
 */

public class NewsPresenterImpl extends BasePresenterImpl implements INewsPresenter {

    private INewsFragment mNewsFragment;
    private Context mContext;

    public NewsPresenterImpl(INewsFragment newsFragment, Context context){
        if(newsFragment == null){
            throw new IllegalArgumentException("newsFragment must not be null");
        }
        this.mNewsFragment = newsFragment;
        this.mContext = context;
    }
    @Override
    public void getNews(int page) {
        mNewsFragment.showProgressDialog();
        Subscription subscription = TxRequest.getTxApi().getWeixin(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TxWeixinResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mNewsFragment.hidProgressDialog();
                        mNewsFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(TxWeixinResponse txWeixinResponse) {
                        mNewsFragment.hidProgressDialog();
                        if(txWeixinResponse.getCode() == 200){
                            mNewsFragment.updateList(txWeixinResponse.getNewslist());
                        }else {
                            mNewsFragment.showError(mContext.getResources().getString(R.string.error));
                        }
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void getNewsFromCache(int page) {

    }
}
