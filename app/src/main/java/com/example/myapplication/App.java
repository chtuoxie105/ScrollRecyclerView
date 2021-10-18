package com.example.myapplication;

import android.app.Application;

import com.lzy.okgo.OkGo;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //没有特殊需要的话,最简单的初始化就可以了
        OkGo.getInstance()
                .init(this);
    }
}
