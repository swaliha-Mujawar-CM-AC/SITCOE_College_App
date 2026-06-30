package com.empire.sitpoly_adminapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.empire.sitpoly_adminapp.R;
import com.empire.sitpoly_adminapp.data.NoticeData;

import java.util.ArrayList;

public class PrivateNoticeAdapter extends RecyclerView.Adapter<PrivateNoticeAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<NoticeData> list;

    public PrivateNoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item_layout,parent,false);

        return new PrivateNoticeAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NoticeData currentItem = list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
