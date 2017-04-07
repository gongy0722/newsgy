package com.crc.news.utils;

import android.app.Application;
import android.util.Log;

import org.xutils.BuildConfig;
import org.xutils.x;

/**
 * Created by gongyuan on 2017/4/7.
 */

public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);  // 注册xutlts框架
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug模式,
        Log.i("crc",this.getClass() + "---> onCreate");
    }
}