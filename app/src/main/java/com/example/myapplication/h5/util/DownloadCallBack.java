package com.example.myapplication.h5.util;

public interface DownloadCallBack {
    /**
     * 进度条
     *
     * @param currentSize
     * @param totalSize
     * @param progress
     */
    void onProgress(long currentSize, long totalSize, int progress);

    /**
     * 下载完成
     *
     * @param filePath
     */
    void onComplete(String filePath);

    /**
     * 下载失败
     * @param message
     */
    void onError(String message);
}
