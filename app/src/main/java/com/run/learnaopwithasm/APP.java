package com.run.learnaopwithasm;

import android.app.Application;

import com.fei.asmdepend.TimeCache;

/**
 * Created by PengFeifei on 2020/4/13.
 */
public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TimeCache.setThresholdInMs(500);
    }
}
