package com.su.gametribe;

import android.app.Application;
import android.content.Context;

import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.XHttpSDK;

/**
 * Created by Administrator on 2019/3/12/012.
 */

public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        XHttpSDK.init(this);
        XHttpSDK.setBaseUrl("http://202.99.114.152:32601/lobby/apks/");
    }

    public static Context getContext() {
        return context;
    }
}
