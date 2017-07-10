package com.dreamguard.stereo.ui.iview;

import com.dreamguard.stereo.bean.weixin.WeixinNews;

import java.util.ArrayList;

/**
 * Created by hailin on 17-7-10.
 */

public interface INewsFragment extends IBaseFragment {
    void updateList(ArrayList<WeixinNews> weixinNewses);
}
