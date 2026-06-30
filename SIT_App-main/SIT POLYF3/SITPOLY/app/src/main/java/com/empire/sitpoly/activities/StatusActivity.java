package com.empire.sitpoly.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.empire.sitpoly.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class StatusActivity extends AppCompatActivity {

    Query reference;
    String verification , uid , classTeacherName;
    RelativeLayout layout;
    DatabaseReference rootRef,finalRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        if (user.isAnonymous()){
            startActivity(new Intent(StatusActivity.this,MainActivity.class));
            finish();
        }



        verification = getIntent().getStringExtra("status");
        uid = getIntent().getStringExtra("userId");
        classTeacherName = getIntent().getStringExtra("classTeacherName");

        layout = findViewById(R.id.layoutRelative);


        if (classTeacherName != null && uid != null){
            SharedPreferences sharedPreferences = getSharedPreferences("data",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("uid",uid);
            editor.apply();

            SharedPreferences sharedPreferences1 = getSharedPreferences("childValues",MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences1.edit();
            editor1.putString("classTeacherName",classTeacherName);
            editor1.apply();

        }

        SharedPreferences getValue = getSharedPreferences("data",MODE_PRIVATE);
        String  studentId = getValue.getString("uid","v");

        SharedPreferences getValue1 = getSharedPreferences("childValues",MODE_PRIVATE);
        String teacherName = getValue1.getString("classTeacherName","s");



//        Toast.makeText(this, studentId, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, teacherName, Toast.LENGTH_SHORT).show();

        if (teacherName!= null && studentId != null) {

            rootRef = FirebaseDatabase.getInstance().getReference();
            finalRef = rootRef.child("Students")
                    .child(teacherName).child("students").child(studentId);
        }else if (classTeacherName != null && uid != null) {
            rootRef = FirebaseDatabase.getInstance().getReference();
            finalRef = rootRef.child("Students")
                    .child(classTeacherName).child("students").child(uid);
        }

        if (!user.isAnonymous()) {

            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String verificationStatus = snapshot.child("verification").getValue(String.class);
                    if (verificationStatus.equals("true")) {
                        startActivity(new Intent(StatusActivity.this, MainActivity.class));
                        finish();
                    } else if (verificationStatus.equals("false")) {
                        Snackbar.make(layout,
                                "Your details are not verified yet!",
                                Snackbar.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(StatusActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };
            finalRef.addListenerForSingleValueEvent(eventListener);
        }


    }
}