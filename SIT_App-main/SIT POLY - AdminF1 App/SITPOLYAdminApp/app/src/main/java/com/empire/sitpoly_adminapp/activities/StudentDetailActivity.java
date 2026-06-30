package com.empire.sitpoly_adminapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.empire.sitpoly_adminapp.R;
import com.empire.sitpoly_adminapp.adapter.StudentAdapter;
import com.empire.sitpoly_adminapp.data.StudentData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentDetailActivity extends AppCompatActivity {

    Query reference;
    RecyclerView recyclerView;
    ArrayList<StudentData> list;
    StudentAdapter adapter;
    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        getSupportActionBar().setTitle("Students Information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        loadingDialog.show();
        reference = FirebaseDatabase.getInstance().getReference("Students")
                .child(getIntent().getStringExtra("classTeacherName").toString()).child("students");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<StudentData>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    StudentData studentData = dataSnapshot.getValue(StudentData.class);
                    list.add(studentData);


                }

                adapter = new StudentAdapter(StudentDetailActivity.this, list);
                recyclerView.setAdapter(adapter);
                loadingDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.refresh_menu,menu);
//
//
//
//        return true;
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == android.R.id.home){
                finish();
        }


        return super.onOptionsItemSelected(item);
    }
}