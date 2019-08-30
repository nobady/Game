package com.su.gametribe;

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


    public static void startApp(Context context,String packageName,String name){
        if (isHasApp(context,packageName)){
            gotoApp(context,packageName);
        }else {
            downloadApp(context,packageName,name);
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

    private static void downloadApp(final Context context, final String packageName, final String name){
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
                install(context,packageName,downFile);
                Toast.makeText(context,downFile.getAbsolutePath() ,0).show();
                runShellCommand("pm install -r "+downFile.getAbsolutePath());
            }
        });
    }

    private static void install(Context context, String packageName, File file) {
        boolean isInstall = silentInstall(context,file.getPath());
        if (isRoot()){
            Toast.makeText(context,"已经root" ,0).show();
        }else {
            Toast.makeText(context,"没有root" ,0).show();
        }
        if (isInstall){
//            startApp(context,packageName,file.getName());
        }
    }

    private static void runShellCommand(String command) {
        Process process = null;
        BufferedReader bufferedReader = null;
        StringBuilder mShellCommandSB =new StringBuilder();
        Log.d("wenfeng", "runShellCommand :" + command);
        mShellCommandSB.delete(0, mShellCommandSB.length());
        String[] cmd = new String[] { "/system/bin/sh", "-c", command }; //调用bin文件
        try {
            byte b[] = new byte[1024];
            process = Runtime.getRuntime().exec(cmd);
            bufferedReader = new BufferedReader(new InputStreamReader(
                process.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                mShellCommandSB.append(line);
            }
            Log.d("wenfeng", "runShellCommand result : " + mShellCommandSB.toString());
            process.waitFor();
        } catch (IOException e) {
            Toast.makeText(BaseApplication.getContext(),"安装失败" + e.getMessage(),0).show();
            e.printStackTrace();
        } catch (InterruptedException e) {
            Toast.makeText(BaseApplication.getContext(),"安装失败" + e.getMessage(),0).show();
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO: handle exception
                }
            }

            if (process != null) {
                process.destroy();
            }
        }
    }

    public static boolean silentInstall(Context context, String apkPath) {
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行pm install命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            Toast.makeText(context,"install msg is " + msg,0).show();
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            if (!msg.contains("Failure")) {
                result = true;
            }
        } catch (Exception e) {
            Toast.makeText(context,"下载失败" + e.getMessage(),0).show();
            Log.e("TAG", e.getMessage(), e);
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                Log.e("TAG", e.getMessage(), e);
            }
        }
        return result;
    }

    public static boolean isRoot() {
        return new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
    }


}
