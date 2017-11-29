package com.fish.staticlistview;

import android.app.Application;

/**
 * Created by fish on 2017/11/29.
 * yuxm_zju@aliyun.com
 */

public class MyApplicatoon extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtil.setApplication(this);
    }
}
