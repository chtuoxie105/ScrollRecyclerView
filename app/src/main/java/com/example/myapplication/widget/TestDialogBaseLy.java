package com.example.myapplication.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class TestDialogBaseLy extends LinearLayout {

    public TestDialogBaseLy(Context context) {
        this(context, null);
    }

    public TestDialogBaseLy(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TestDialogBaseLy(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
