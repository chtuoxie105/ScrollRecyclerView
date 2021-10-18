package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.TestAdapter;
import com.example.myapplication.widget.TestDialogBaseLy;

public class TestDialog extends Dialog {
    TestDialogBaseLy baseLy;
    RecyclerView recyclerView;

    public TestDialog(@NonNull Context context) {
        super(context, R.style.dialog_fragment);
        setContentView(R.layout.test_dialog);
        initWindow();
        baseLy = findViewById(R.id.base_test_ly);
        recyclerView = findViewById(R.id.test_list_view);
        TestAdapter testAdapter = new TestAdapter();
        recyclerView.setAdapter(testAdapter);
        testAdapter.testData();
    }

    private int mWindowHeight = 0;
    private int mLastHeight = 0;

    private void initWindow() {
        Window window = getWindow();
        // 设置屏幕高度和动画
        if (null != window) {
            WindowManager.LayoutParams lp = window.getAttributes();
            if (mWindowHeight == 0) {
                window.setWindowAnimations(R.style.dialog_animation);
                Point point = new Point();
                WindowManager wm = window.getWindowManager();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    wm.getDefaultDisplay().getRealSize(point);
                } else {
                    wm.getDefaultDisplay().getSize(point);
                }
                int height = point.y;
                lp.height = (int) (height * 0.7);
                mWindowHeight = lp.height;
            } else {
                lp.height = mWindowHeight;
            }
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.gravity = Gravity.BOTTOM;
            window.setAttributes(lp);
        }
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }


    private void updateWindowHeight(int offset) {
        Window window = getWindow();
        if (null != window) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.height += offset;
            if (lp.height < 0) {
                lp.height = 0;
            }
            if (lp.height > mWindowHeight) {
                lp.height = mWindowHeight;
            }
//            FrameLayout.LayoutParams flp = (FrameLayout.LayoutParams) baseLy.getLayoutParams();
//            flp.height = lp.height;
//            baseLy.setLayoutParams(flp);
            mLastHeight = lp.height;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.gravity = Gravity.BOTTOM;
            window.setAttributes(lp);
        }
    }

    @Override
    public void show() {
        initWindow();
        super.show();
    }

    //0:初始状态，1：子类处理，2：自己处理滑动
    private int isIntercept = 0;
    //记录上次的位置
    private float mLastY = 0;

    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        Log.e("11", "========================");
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mLastY = ev.getRawY();
            Log.e("11", "===========按下");
        }

        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            Log.e("11", "===========移动>>:" + isIntercept);
            float curY = ev.getRawY();
            if (isIntercept == 0) {
                float offset = curY - mLastY;
                if (offset != 0) {
                    if (offset < 0) {
                        //上滑
                        isIntercept = 1;
                    } else {
                        //下滑
                        if (interceptChild()) {
                            isIntercept = 2;
                        } else {
                            isIntercept = 1;
                        }
                    }
                }
            } else if (isIntercept == 2) {
                float offset = mLastY - curY;
                Log.e("11", "offset>>>:" + offset);
                updateWindowHeight((int) offset);
                mLastY = curY;
                return true;
            }
        }

        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            Log.e("11", "===========抬起X");
            if (isIntercept == 2) {
                boolean needClose = mLastHeight < mWindowHeight / 2;
                updateWindowHeight(mWindowHeight);
                if (needClose) {
                    //消失
                    dismiss();
                }
                isIntercept = 0;
                return true;
            }
            isIntercept = 0;
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean interceptChild() {
        int count = baseLy.getChildCount();
        for (int i = 0; i < count; i++) {
            boolean canScroll = canChildScrollUp(baseLy.getChildAt(i));
            if (canScroll) {
                return false;
            }
        }
        return true;
    }

    public boolean canChildScrollUp(View drawer) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (drawer instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) drawer;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(drawer, -1) || drawer.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(drawer, -1);
        }
    }
}
