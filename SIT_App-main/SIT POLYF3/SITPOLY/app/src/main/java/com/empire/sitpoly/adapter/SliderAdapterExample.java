package com.empire.sitpoly.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.empire.sitpoly.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapterExample  extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH>{

    private Context context;
    private int mCount;

    public SliderAdapterExample(Context context) {
        this.context = context;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });


        switch (position) {
            case 0:
                //  viewHolder.textViewDescription.setText("This is slider item " + position);
                //  viewHolder.textViewDescription.setTextSize(16);
                //viewHolder.textViewDescription.setTextColor(Color.WHITE);
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.sit1)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 2:
                // viewHolder.textViewDescription.setText("This is slider item " + position);
                //  viewHolder.textViewDescription.setTextSize(16);
                //   viewHolder.textViewDescription.setTextColor(Color.WHITE);
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.sit3)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;

            case 3:
                // viewHolder.textViewDescription.setText("This is slider item " + position);
                //  viewHolder.textViewDescription.setTextSize(16);
                //   viewHolder.textViewDescription.setTextColor(Color.WHITE);
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.abc)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;

            default:
                //viewHolder.textViewDescription.setTextSize(29);
                // viewHolder.textViewDescription.setTextColor(Color.WHITE);
                // viewHolder.textViewDescription.setText("Ohhhh! look at this!");
                viewHolder.imageGifContainer.setVisibility(View.VISIBLE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.sit4)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);

                break;

        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mCount;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            //  textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }


}
