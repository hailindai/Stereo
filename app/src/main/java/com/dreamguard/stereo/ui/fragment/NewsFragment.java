package com.dreamguard.stereo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dreamguard.stereo.R;
import com.dreamguard.stereo.bean.weixin.WeixinNews;
import com.dreamguard.stereo.presenter.INewsPresenter;
import com.dreamguard.stereo.ui.adapter.NewsAdapter;
import com.dreamguard.stereo.ui.iview.INewsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hailin on 17-7-7.
 */

public class NewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, INewsFragment {


    NewsAdapter newsAdapter;

    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder mUnbinder;
    private INewsPresenter mNewsPresenter;
    private ArrayList<WeixinNews> weixinNewses = new ArrayList<>();
    private int currentPage = 1;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean loading = false;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;

    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mNewsPresenter.unsubcrible();
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hidProgressDialog() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void updateList(ArrayList<WeixinNews> weixinNewses) {

    }
}
