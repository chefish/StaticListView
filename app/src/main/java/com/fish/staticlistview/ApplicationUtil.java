package com.fish.staticlistview;

import android.app.Application;
import android.content.Context;

public class ApplicationUtil {
    public static Context mAppContext;
    public static Application mApplication;

    public static Context getApplicationContext(){
        return mAppContext;
    }

    public static Context getApplication(){
        return mApplication;
    }

    public static void setApplication(Application context){
        mApplication = context;
        mAppContext = mApplication == null ? null : mApplication.getApplicationContext();
    }
}
