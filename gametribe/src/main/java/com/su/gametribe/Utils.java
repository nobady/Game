package com.su.gametribe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.xuexiang.xhttp2.XHttp;
import com.xuexiang.xhttp2.callback.CallBack;
import com.xuexiang.xhttp2.callback.DownloadProgressCallBack;
import com.xuexiang.xhttp2.callback.ProgressLoadingCallBack;
import com.xuexiang.xhttp2.exception.ApiException;
import com.xuexiang.xhttp2.subsciber.ProgressDialogLoader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

/**
 * Created by kingdee on 2019-08-29.
 */
public class Utils {


    public static void startApp(Activity context,String packageName,String name){
        if (isHasApp(context,packageName)){
            gotoApp(context,packageName);
        }else {
            downloadApp(context,packageName,name);
        }
    }

    public static void gotoApp(Context context,String packageName){
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

    private static void downloadApp(final Activity context, final String packageName, final String name){
        final File downDir = new File(Environment.getExternalStorageDirectory() + "/download/");

        if (!downDir.exists()){
            downDir.mkdirs();
        }
        XHttp.downLoad("http://120.192.66.36:18014/epg-karaoke-ott-zte/apk/apks/"+name).savePath(downDir.getPath())
            .saveName(name).execute(new ProgressDialogCallback<String>(context){
            @Override
            public void onComplete(String path) {
                super.onComplete(path);
                File downFile = new File(downDir.getPath(),name);
                mPackageName = packageName;
                install(context,downFile.getAbsolutePath());

//                Toast.makeText(context,downFile.getAbsolutePath() ,0).show();
//                runShellCommand("pm install -r "+downFile.getAbsolutePath());
            }
        });
    }

public static String mPackageName;
    public static void install(Activity context, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)),
            "application/vnd.android.package-archive");
        context.startActivityForResult(intent,3);
    }


}
