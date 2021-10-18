package com.example.myapplication.h5.util;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.base.Request;

import java.io.File;

public class DownManager {
    private static final String TAG = "tag";

    public static void download(String url, String destPath, Context context, DownloadCallBack progressCallback) {
        Log.e(TAG, "======download=======");
        String destFileDir = context.getExternalFilesDir("") + File.separator;
        Log.e(TAG, "开始下载文件>>destFileDir>>>:" + destFileDir);
        String mDestFileName = url.substring(url.lastIndexOf('/') + 1);
        Log.e(TAG, "开始下载文件>>:" + "=====mDestFileName===" + mDestFileName);
        OkGo.<File>get(url).tag(context).execute(new FileCallback(destFileDir, mDestFileName) { //文件下载时指定下载的路径以及下载的文件的名称
            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
                Log.e(TAG, "开始下载文件");
            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                String mBasePath = response.body().getAbsolutePath();
                Log.e(TAG, "下载文件成功:" + mBasePath);
                progressCallback.onComplete(mBasePath);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                Log.e(TAG, "下载文件完成");
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);
                Log.e(TAG, "下载文件出错:" + response.message());

            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                float dLProgress = progress.fraction;
                Log.e(TAG, "文件下载的进度:" + dLProgress);
            }
        });

    }
}
