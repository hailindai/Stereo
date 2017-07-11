package com.dreamguard.stereo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dreamguard.stereo.R;
import com.dreamguard.stereo.bean.weixin.WeixinNews;
import com.dreamguard.stereo.presenter.INewsPresenter;
import com.dreamguard.stereo.presenter.impl.NewsPresenterImpl;
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


    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.swipeToLoadLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder mUnbinder;
    private INewsPresenter mNewsPresenter;
    private NewsAdapter newsAdapter;
    private ArrayList<WeixinNews> mNewses = new ArrayList<>();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mNewsPresenter.unsubcrible();
    }

    private void initView() {
        showProgressDialog();
        swipeRefreshLayout.setOnRefreshListener(this);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        swipeTarget.setLayoutManager(mLinearLayoutManager);
        swipeTarget.setHasFixedSize(true);
        swipeTarget.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        swipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //向下滚动
                {
                    visibleItemCount = mLinearLayoutManager.getChildCount();
                    totalItemCount = mLinearLayoutManager.getItemCount();
                    pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        onLoadMore();
                    }
                }
            }
        });
        newsAdapter = new NewsAdapter(getActivity(), mNewses);
        swipeTarget.setAdapter(newsAdapter);
        mNewsPresenter.getNews(1);
        onRefresh();
    }

    private void initData() {
        mNewsPresenter = new NewsPresenterImpl(this, getActivity());
    }

    public void onLoadMore() {
        mNewsPresenter.getNews(currentPage);
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        mNewses.clear();
        newsAdapter.notifyDataSetChanged();
        mNewsPresenter.getNews(1);
    }

    @Override
    public void showProgressDialog() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidProgressDialog() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            loading = false;
        }
        if (progressBar != null)
            progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        if (swipeTarget != null) {
            Snackbar.make(swipeTarget, getString(R.string.error) + error, Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNewsPresenter.getNews(currentPage);
                }
            }).show();
        }
    }

    @Override
    public void updateList(ArrayList<WeixinNews> weixinNewses) {
        currentPage++;
        mNewses.addAll(weixinNewses);
        newsAdapter.notifyDataSetChanged();
    }
}
