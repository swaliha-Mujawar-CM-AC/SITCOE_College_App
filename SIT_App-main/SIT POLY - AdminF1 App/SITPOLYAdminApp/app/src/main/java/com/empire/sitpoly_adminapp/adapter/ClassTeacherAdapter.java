package com.empire.sitpoly_adminapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.empire.sitpoly_adminapp.R;
import com.empire.sitpoly_adminapp.activities.StudentDetailActivity;
import com.empire.sitpoly_adminapp.data.ClassTeacherData;

import java.util.ArrayList;

public class ClassTeacherAdapter extends RecyclerView.Adapter<ClassTeacherAdapter.ViewHolder> {

    Context context;
    ArrayList<ClassTeacherData> classTeacherData;

    public ClassTeacherAdapter(Context context, ArrayList<ClassTeacherData> classTeacherData) {
        this.context = context;
        this.classTeacherData = classTeacherData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.teacher_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


            holder.tv.setText(classTeacherData.get(position).getClassTeacherName().toString());
            holder.setData(classTeacherData.get(position).getClassTeacherName().toString());



    }

    @Override
    public int getItemCount() {
        return classTeacherData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        public ViewHolder(@NonNull View itemView) {
                super(itemView);

            tv =itemView.findViewById(R.id.teacherName1);

        }

        public void setData(String toString) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, StudentDetailActivity.class);
                    intent.putExtra("classTeacherName", tv.getText().toString());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
