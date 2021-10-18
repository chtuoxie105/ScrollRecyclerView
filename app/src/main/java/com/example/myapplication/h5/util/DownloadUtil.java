package com.example.myapplication.h5.util;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

public class DownloadUtil {
    private static DownloadUtil instance;
    private Context context;

    private DownloadUtil() {
    }

    public static DownloadUtil getInstance() {
        if (null != instance) {
            return instance;
        } else {
            instance = new DownloadUtil();
        }
        return instance;
    }

    /**
     * 初始化上下文
     *
     * @param context
     */
    public void init(Context context) {
        this.context = context;
    }

    public String downloadPath(String url) {
        String path = null;
        if (url.contains("/")) {
            String filePath = url.substring(url.lastIndexOf('/') + 1);
            path = context.getExternalFilesDir(null) + File.separator + filePath;
            return path;
        } else {
            return path;
        }


    }

    public boolean isPdf(String path) {
        return !TextUtils.isEmpty(path) && path.endsWith("pdf");
    }
}
