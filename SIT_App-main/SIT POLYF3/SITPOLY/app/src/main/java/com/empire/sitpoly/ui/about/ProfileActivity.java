package com.empire.sitpoly.ui.about;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.empire.sitpoly.R;
import com.empire.sitpoly.activities.LoginActivity;
import com.empire.sitpoly.activities.MainActivity;
import com.empire.sitpoly.data.StudentData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView name, enroll, year, dept, classTeacher, mail;
    private DatabaseReference reference;


    FirebaseAuth firebaseAuth;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private TextView tv;
    String email,studentId,teacherName;

    AlertDialog dialog;
    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        name = findViewById(R.id.Name);
        enroll = findViewById(R.id.Enrollment);
        year = findViewById(R.id.studentyear);
        dept = findViewById(R.id.dept);
        classTeacher = findViewById(R.id.classteacher);
        mail = findViewById(R.id.Email);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        assert firebaseUser != null;
        email = firebaseUser.getEmail();

        SharedPreferences getValue = getSharedPreferences("data",MODE_PRIVATE);
        studentId = getValue.getString("uid","v");

        SharedPreferences getValue1 = getSharedPreferences("childValues",MODE_PRIVATE);
         teacherName = getValue1.getString("classTeacherName","s");

        if (user.isAnonymous()){
            builder = new AlertDialog.Builder(ProfileActivity.this);
            builder.setTitle("Permission Detained")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("To view this content, You need to login as a Student! Click on close to move back")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).setCancelable(false);

            dialog = builder.create();
            dialog.show();

        } else {
            fetchDetails();
        }
    }

    private void fetchDetails(){
        reference = FirebaseDatabase.getInstance().getReference().child("Students").child(teacherName).child("students").child(studentId);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // for (DataSnapshot snapshot1: snapshot.getChildren()){

                StudentData data = snapshot.getValue(StudentData.class);
                name.setText(data.getName());
                enroll.setText(data.getEnrollNo());
                dept.setText(data.getDepartment());
                year.setText(data.getYear());
                mail.setText(email);
                classTeacher.setText(data.getClassTeacher());


                //   }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}