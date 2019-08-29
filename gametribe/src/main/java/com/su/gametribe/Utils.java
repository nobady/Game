package com.su.gametribe;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.callback.CallBack;
import com.xuexiang.xhttp2.callback.DownloadProgressCallBack;
import com.xuexiang.xhttp2.callback.ProgressLoadingCallBack;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.subsciber.ProgressDialogLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by kingdee on 2019-08-29.
 */
public class Utils {


    public static void startApp(Context context,String packageName,String name){
        if (isHasApp(context,packageName)){
            gotoApp(context,packageName);
        }else {
            downloadApp(context,name);
        }
    }

    private static void gotoApp(Context context,String packageName){
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    private static boolean isHasApp(Context context,String packageName){
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        android.content.pm.ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(packageName, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private static void downloadApp(final Context context, final String name){
        final File downDir = new File(Environment.getExternalStorageDirectory() + "/download/");

        if (!downDir.exists()){
            downDir.mkdirs();
        }
        XHttp.downLoad("http://202.99.114.152:32601/lobby/apks/"+name).savePath(downDir.getPath())
            .saveName(name).execute(new ProgressDialogCallback<String>(context){
            @Override
            public void onComplete(String path) {
                super.onComplete(path);
                File downFile = new File(downDir.getPath(),name);
                install(context,downFile);
            }
        });
    }

    private static void install(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");

        context.startActivity(intent);
    }

}
