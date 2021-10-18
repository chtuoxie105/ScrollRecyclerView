package com.example.myapplication.list;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class TestMyList extends RecyclerView {
    // 一个滚动对象
    private Scroller mScroller;
    private int mLastX = 0;
    public TestMyList(@NonNull Context context) {
        this(context,null);
    }

    public TestMyList(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public TestMyList(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    // 一个初始化方法，传入了一个上下文对象，用来初始化滚动对象
    private void init(Context context){
        mScroller = new Scroller(context);
    }
    // 重写了计算滚动方法
    @Override
    public void computeScroll() {
        if(mScroller!=null && mScroller.computeScrollOffset()){
            scrollBy(mLastX - mScroller.getCurrX(), 0);
            mLastX = mScroller.getCurrX();
            postInvalidate();
        }
    }
}
