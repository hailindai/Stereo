package com.dreamguard.stereo.ui.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dreamguard.stereo.R;
import com.dreamguard.stereo.utils.NetWorkUtil;

import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsActivity extends AppCompatActivity {

    @BindView(R.id.wv_detail)
    WebView wvDetail;

    @BindView(R.id.pb_web)
    ProgressBar pbWeb;

    String url;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        setToolbar();

        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");


        WebSettings webSettings = wvDetail.getSettings();
        if (!NetWorkUtil.isNetWorkAvailable(this))
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        else
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        wvDetail.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlTo) {
                //处理自动跳转到浏览器的问题
                view.loadUrl(urlTo);
                return true;
            }
        });
        wvDetail.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (pbWeb != null) {
                    if (newProgress == 100) {
                        pbWeb.setVisibility(View.GONE);
                    } else {
                        if (pbWeb.getVisibility() == View.GONE) {
                            pbWeb.setVisibility(View.VISIBLE);
                        }
                        pbWeb.setProgress(newProgress);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }

            //显示全屏按钮
            private View myView = null;
            private CustomViewCallback myCallback = null;

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                if (myCallback != null) {
                    myCallback.onCustomViewHidden();
                    myCallback = null;
                    return;
                }
                ViewGroup parent = (ViewGroup) wvDetail.getParent();
                parent.removeView(wvDetail);
                parent.addView(view);
                myView = view;
                myCallback = callback;
            }

            @Override
            public void onHideCustomView() {
                if (myView != null) {
                    if (myCallback != null) {
                        myCallback.onCustomViewHidden();
                        myCallback = null;
                    }
                    ViewGroup parent = (ViewGroup) myView.getParent();
                    parent.removeView(myView);
                    parent.addView(wvDetail);
                    myView = null;
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.confirm();
                return super.onJsAlert(view, url, message, result);
            }
        });
        wvDetail.loadUrl(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            wvDetail.getClass().getMethod("onResume").invoke(wvDetail, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            wvDetail.getClass().getMethod("onPause").invoke(wvDetail, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wvDetail != null) {
            ((ViewGroup) wvDetail.getParent()).removeView(wvDetail);
            wvDetail.destroy();
            wvDetail = null;
        }
    }

    public void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getSupportFragmentManager().popBackStackImmediate()) {
                    finish();
                }
            }
        });
    }

}
