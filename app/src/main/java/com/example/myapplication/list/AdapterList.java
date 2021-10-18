package com.example.myapplication.list;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterList extends RecyclerView.Adapter<AdapterList.Vh> {
    private int width = 0;
    private List<String> data = new ArrayList<>();

    public AdapterList(int width) {
        this.width = width;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<String> getData() {
        return data;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.ap_list_item_layout, null);
        if (width > 0) {
            LinearLayout.LayoutParams lp;
            if (null != view.getLayoutParams()) {
                lp = (LinearLayout.LayoutParams) view.getLayoutParams();
                lp.weight = width;
                lp.height = width;
            } else {
                lp = new LinearLayout.LayoutParams(width, width);
            }
            view.setLayoutParams(lp);
        }
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Vh extends RecyclerView.ViewHolder {

        public Vh(@NonNull View itemView) {
            super(itemView);
            if (null != onClickListener) {
                itemView.setOnClickListener(onClickListener);
            }
        }
    }

    View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
