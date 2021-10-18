package com.example.myapplication.h5;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.h5.p.OnPermission;
import com.example.myapplication.h5.p.XXPermissions;
import com.example.myapplication.h5.util.DownManager;
import com.example.myapplication.h5.util.DownloadCallBack;

import java.util.List;

public class H5TestActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5_layout);
        webView = findViewById(R.id.file_viewctrl);
        setting();
        requestPermissions();
//    load("https://dggelec.oss-cn-beijing.aliyuncs.com/20210916-14400d2f9bf243f380337f41f68d3933.pdf");
        String pdfUrl = "https://dggelec.oss-cn-beijing.aliyuncs.com/20210916-14400d2f9bf243f380337f41f68d3933.pdf";
        String data = "<iframe src='http://docs.google.com/gview?embedded=true&url=" + pdfUrl + "'" + " width='100%' height='100%' s	tyle='border: none;'></iframe>";
//        webView.loadData(data, "text/html", "UTF-8");

    }

    private void setting() {
        WebView mWebView = webView;
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(true);
    }


    public void requestPermissions() {
        //读写权限用于X5的提前初始化，否则在使用时再去初始化X5会失败，导致文件预览失败，就需要重新启动应用
        String[] PERMISSION = {
                Manifest.permission.READ_EXTERNAL_STORAGE//外部存储
                , Manifest.permission.WRITE_EXTERNAL_STORAGE,// 外部存储
                Manifest.permission.ACCESS_NETWORK_STATE//  网络状态
                //Manifest.permission.ACCESS_COARSE_LOCATION,//位置
                , Manifest.permission.READ_PHONE_STATE // 手机状态。
        };
        log("申请权限");
        XXPermissions.with(this).permission(PERMISSION).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean all) {
                log("申请权限成功");
                down("https://dggelec.oss-cn-beijing.aliyuncs.com/20210916-14400d2f9bf243f380337f41f68d3933.pdf");
            }

            @Override
            public void noPermission(List<String> denied, boolean never) {
                log("申请权限失败");
            }
        });

    }

    private void load(String filePath) {
        String path = "file:///android_asset/h5/pdf.html?" + filePath;
        log("加载地址>>:" + path);
        webView.loadUrl(path);
//        webView.loadUrl("file:///android_asset/h5/viewer.html?file=" + filePath);
//        webView.loadUrl("file:///android_asset/h5/mypdf.html?pdfpath=" + filePath);
    }

    private void down(String url) {
//        String path = DownloadUtil.getInstance().downloadPath(url);
        Log.e("progress=====>", url + "%");
        DownManager.download(url, url, this, new DownloadCallBack() {
            @Override
            public void onProgress(long currentSize, long totalSize, int progress) {
                Log.d("progress=====>", progress + "%");
            }

            @Override
            public void onComplete(String filePath) {
                Log.d("progress=====>", "下载完成" + filePath);
                load(filePath);
            }

            @Override
            public void onError(String message) {
            }
        });
    }

    private void log(String msg) {
        Log.e("11", "日志>>>:" + msg);
    }
}
