package com.empire.sitpoly_adminapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.empire.sitpoly_adminapp.R;
import com.empire.sitpoly_adminapp.data.StudentData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    Context context;
    ArrayList<StudentData> studentData;


    public StudentAdapter(Context context, ArrayList<StudentData> studentData) {
        this.context = context;
        this.studentData = studentData;
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(context).inflate(R.layout.student_detail_row, parent, false);
         StudentAdapter.ViewHolder viewHolder = new StudentAdapter.ViewHolder(view);
         return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {

        holder.dept.setText(studentData.get(position).getDepartment().toString());
        holder.year.setText(studentData.get(position).getYear().toString());
        holder.name.setText(studentData.get(position).getName().toString());
        holder.enrollNo.setText(studentData.get(position).getEnrollNo().toString());
        holder.classTeacher.setText(studentData.get(position).getClassTeacher().toString());
//        holder.email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());

        holder.onMethod(studentData.get(position).getVerification(),
                studentData.get(position).getClassTeacher(),
                studentData.get(position).getStudentId());


    }



    @Override
    public int getItemCount() {
        return studentData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView dept;
        TextView year;
        TextView name;
        TextView enrollNo;
        TextView classTeacher;
        TextView email;
        final Button verify , disable;
        LinearLayout linearLayout;
        Query reference;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dept =itemView.findViewById(R.id.student_dept);
            year = itemView.findViewById(R.id.student_year);
            enrollNo = itemView.findViewById(R.id.student_enroll_no);
            name = itemView.findViewById(R.id.student_name);
            classTeacher = itemView.findViewById(R.id.student_class_teacher);
         //   email = itemView.findViewById(R.id.student_email);
            verify = itemView.findViewById(R.id.verify);
            disable = itemView.findViewById(R.id.disable);
            linearLayout = itemView.findViewById(R.id.linear);


        }

         void onMethod(String verification, String classTeacher, String studentId) {


             DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
             DatabaseReference finalRef = rootRef.child("Students")
                     .child(classTeacher).child("students").child(studentId);


             ValueEventListener eventListener = new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     final String verificationStatus = snapshot.child("verification").getValue(String.class);

                     if (verificationStatus.equals("true")){

                         verify.setText("verified");
                         verify.setEnabled(false);


                     }else if (verificationStatus.equals("false")){
                         verify.setText("verify");


                     }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             };
             finalRef.addListenerForSingleValueEvent(eventListener);

             verify.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     FirebaseDatabase.getInstance().getReference().child("Students").child(classTeacher)
                             .child("students").child(studentId).child("verification").setValue("true");

                        verify.setText("verified");
                        verify.setEnabled(false);



                 }
             });





        }


    }
}
