package com.example.myapplication.list;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.ChildFragment;
import com.example.myapplication.R;
import com.example.myapplication.TestDialog;
import com.example.myapplication.Testkotlin;
import com.example.myapplication.refresh.CpsSmartRefreshLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainTestListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    View line;
    LinearLayout lineLy;
    int scrollWidth = 0;
    int scrollX = 0;
    int totalScrollWidth = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_layout);
        recyclerView = findViewById(R.id.test_list_recycler_view);
        line = findViewById(R.id.test_line);
        lineLy = findViewById(R.id.test_line_ly);
        int itemWidth = getWight();
        AdapterList adapterList = new AdapterList(itemWidth);
        adapterList.setOnClickListener(v -> log("带年纪>>>>:"));
        recyclerView.setAdapter(adapterList);
        List<String> list = buildData();
        if (list.size() <= 10) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        } else {
            GridLayoutManager gm = new GridLayoutManager(this, 2);
            gm.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(gm);
            scrollWidth = ((list.size() / 2) - 5) * itemWidth;
            int allWidth = (list.size() / 2) * itemWidth;
            log("allWidth>>>>:" + allWidth);
        }

        adapterList.setData(list);
        if (list.size() > 10) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
//                log("dx>>>:" + dx);
//                int w = recyclerView.getWidth();
//                log("w>>>:" + w);
                    log("==========================================");
                    int extent = recyclerView.computeHorizontalScrollExtent();
                    log("extent>>>:" + extent);
                    if (scrollX == 0) {
                        scrollWidth = extent - scrollWidth;
                        log("scrollWidth>?>>滑动宽度>>:" + scrollWidth);
                    }
                    if (totalScrollWidth == 0) {
                        totalScrollWidth = lineLy.getWidth() - line.getWidth();
                    }
                    log("dx>>>:" + dx);
                    if (scrollX + dx < 0) {
                        return;
                    }
                    scrollX += dx;
                    log("scrollX>>>>:" + scrollX);

                    int w = (scrollX * totalScrollWidth) / scrollWidth;
                    log("100>>>滑动宽度>>:" + w);
                    if (w < 0) {
                        w = 0;
                    }
                    if (w > totalScrollWidth) {
                        w = totalScrollWidth;
                    }
                    line.setTranslationX(w);
                }
            });
        }
    }


    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    private int dp2Px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private List<String> buildData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            data.add("");
        }
        return data;
    }

    private int getWight() {
        return getWindowManager().getDefaultDisplay().getWidth() / 5;
    }

    private void log(String msg) {
        Log.e("11", "日志>>>:" + msg);
    }
}
