package com.su.gametribe;

import android.app.ProgressDialog;
import android.content.Context;

import com.xuexiang.xhttp2.callback.DownloadProgressCallBack;
import com.xuexiang.xhttp2.exception.ApiException;

/**
 * Created by kingdee on 2019-08-29.
 */
public class ProgressDialogCallback<T> extends DownloadProgressCallBack<T> {

    private ProgressDialog mDialog;
    private Context mContext;

    public ProgressDialogCallback(Context context) {
        mContext = context;
    }

    @Override
    public void update(long downLoadSize, long totalSize, boolean done) {
        mDialog.setMax((int) totalSize);
        mDialog.setProgress((int) downLoadSize);
    }

    @Override
    public void onComplete(String path) {
        mDialog.dismiss();
    }

    @Override
    public void onStart() {
        mDialog = new ProgressDialog(mContext);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 标题
        mDialog.setTitle("下载提示");
        // 设置ProgressDialog 提示信息
        mDialog.setMessage("当前下载进度:");
        // 设置ProgressDialog 是否可以按退回按键取消
        mDialog.setCancelable(false);
        mDialog.show();
    }

    @Override
    public void onError(ApiException e) {

        mDialog.dismiss();
    }
}
