package com.example.myapplication.list;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MainTestListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    View line;
    LinearLayout lineLy;

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
        }

        adapterList.setData(list);
        if (list.size() > 10) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    log("==========================================");
                    test1();
                }
            });
        }
    }

    int totalRange = 0;

    private void test1() {
        //整体的总宽度，注意是整体，包括在显示区域之外的
        //滚动条表示的总范围
        int temp = recyclerView.computeHorizontalScrollRange();
        if (temp > totalRange) {
            totalRange = temp;
        }
        //滑块的偏移量
        int offset = recyclerView.computeHorizontalScrollOffset();
        //可视区域长度
        int extent = recyclerView.computeHorizontalScrollExtent();
        //滑出部分在剩余范围的比例
        float proportion = (float) (offset * 1.0 / (totalRange - extent));
        //计算滚动条宽度
        float transMaxRange = lineLy.getWidth() - line.getWidth();
        //设置滚动条移动
        float w = transMaxRange * proportion;
        log("滑动记录>>>>:" + w);
        line.setTranslationX(w);
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
