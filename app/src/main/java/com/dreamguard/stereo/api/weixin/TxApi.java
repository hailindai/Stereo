package com.dreamguard.stereo.api.weixin;


import com.dreamguard.stereo.bean.weixin.TxWeixinResponse;
import com.dreamguard.stereo.config.Config;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hailin on 2017/7/10.
 */
public interface TxApi {
    @GET("/wxnew/?key=" + Config.TX_APP_KEY + "&num=20")
    Observable<TxWeixinResponse> getWeixin(@Query("page") int page);
}