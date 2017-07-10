package com.dreamguard.stereo;

import android.app.Application;
import android.content.Context;

/**
 * Created by hailin on 17-7-7.
 */

public class StereoApplication extends Application {
    private static StereoApplication stereoApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        stereoApplication = this;
    }

    public static Context getContext(){
        return stereoApplication;
    }
}
