package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.refresh.CpsSmartRefreshLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainTestActivity extends AppCompatActivity {
    //顶部悬浮的标题、滑动显示的标题，toolBar占位的标题
    private TextView tvTopTitleBar, tvScrollTitleBar, tvToolBarTopTitleBar;
    private AppBarLayout appBarLayout;
    private ImageView bannerImg;
    private TabLayout tabLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TestFgAp testFgAp = new TestFgAp(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.test_view_pager);
        viewPager.setAdapter(testFgAp);
        CpsSmartRefreshLayout cpsSmartRefreshLayout = findViewById(R.id.test_refresh);
        cpsSmartRefreshLayout.setEnableLoadMore(false);

        tvTopTitleBar = findViewById(R.id.test_top_top_bar);
        tvScrollTitleBar = findViewById(R.id.test_top_scroll_bar);
        tvToolBarTopTitleBar = findViewById(R.id.test_top_top_toolbar_bar);

        collapsingToolbarLayout = findViewById(R.id.coll_app_bar_ly);

        tabLayout = findViewById(R.id.tab_ly);
        bannerImg = findViewById(R.id.test_banner);

        appBarLayout = findViewById(R.id.app_bar_ly);
        addOnOffsetChangedListener();
        tvTopTitleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
         Testkotlin.Companion.test(5);
    }

    private void addOnOffsetChangedListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                log("----------------------------");
                log("verticalOffset>>:" + verticalOffset);
                int[] location = new int[2];
                if (statusHeight == 0) {
                    bannerImg.getLocationOnScreen(location);
                    statusHeight = location[1];
                }

                if (Math.abs(verticalOffset) < bannerImg.getHeight() - 50) {
//                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) bannerImg.getLayoutParams();
//                    lp.height += verticalOffset;
//                    bannerImg.setLayoutParams(lp);
                }

                tvScrollTitleBar.getLocationOnScreen(location);
                int y = location[1] - statusHeight;

                //显示顶部占位
                if (y <= 0) {
                    tvTopTitleBar.setVisibility(View.VISIBLE);
                } else {
                    tvTopTitleBar.setVisibility(View.GONE);
                }
                //显示Tab上面的占位toolbar
                int heg = collapsingToolbarLayout.getHeight();
                log("heg>>>:" + heg);
                int h = tabLayout.getHeight();
                log("hhhh>>>:" + h);
                if (lastOffset < 0) {
                    if (lastOffset < verticalOffset) {
                        //下拉
                    } else {
                        //上滑

                    }

                    if (Math.abs(verticalOffset) <= collapsingToolbarLayout.getHeight() - tabLayout.getHeight()) {
                        tvToolBarTopTitleBar.setVisibility(View.GONE);
                    } else {
                        tvToolBarTopTitleBar.setVisibility(View.VISIBLE);
                    }
                }


                lastOffset = verticalOffset;
            }
        });
    }

    private int lastOffset = 0;
    private int statusHeight = 0;
    TestDialog testDialog;

    private void showDialog() {
        if (null == testDialog) {
            testDialog = new TestDialog(this);
        }
        testDialog.show();
    }

    public class TestFgAp extends FragmentStatePagerAdapter {
        List<Fragment> list = new ArrayList<>();

        public TestFgAp(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            list.add(new ChildFragment());
            list.add(new ChildFragment());
            list.add(new ChildFragment());
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    private void log(String msg) {
//        Log.e("11", "日志>>>:" + msg);
    }
}
