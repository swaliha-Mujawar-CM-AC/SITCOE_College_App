package com.empire.sitpoly.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.empire.sitpoly.R;
import com.empire.sitpoly.activities.ImageActivity;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private Context context;

    public GalleryAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    private List<String> list;

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.gallery_image,parent,false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        Glide.with(context).load(list.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image_item);
        }
    }
}
