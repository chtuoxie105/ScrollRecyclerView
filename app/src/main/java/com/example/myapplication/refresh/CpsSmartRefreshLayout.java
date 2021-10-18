package com.example.myapplication.refresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.example.myapplication.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;

public class CpsSmartRefreshLayout  extends SmartRefreshLayout {
    private Context context;

    public CpsSmartRefreshLayout(Context context) {
        this(context, null);
    }

    public CpsSmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        intHeader();
    }

    /**
     * 初始化头布局
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private void intHeader() {
        Drawable drawable = getResources().getDrawable(R.drawable.toast_ic_load);
        drawable.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(R.color.globalT4), PorterDuff.Mode.SRC_ATOP));
        setRefreshHeader(new CpsClassicsHeader(context).setLastUpdateText(null)
                .setEnableLastTime(false)
                .setProgressDrawable(drawable));
        CpsClassicsFooter cpsClassicsFooter = new CpsClassicsFooter(getContext()).setProgressDrawable(drawable).setDrawableMarginRight(8);
        cpsClassicsFooter.setBackgroundResource(R.color.cp_color_section_bg);
        setRefreshFooter(cpsClassicsFooter);
    }

    /**
     * 自定义需要的下拉刷新头
     *
     * @param refreshHeader
     */
    public void setCustomHeader(RefreshHeader refreshHeader) {
        setRefreshHeader(refreshHeader);
    }

    /**
     * 自定义需要的上拉刷新
     *
     * @param refreshFooter
     */
    public void setCustomFooter(RefreshFooter refreshFooter) {
        setRefreshFooter(refreshFooter);
    }

}
