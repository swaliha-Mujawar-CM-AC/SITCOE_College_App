package com.empire.sitpoly_adminapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.empire.sitpoly_adminapp.R;
import com.empire.sitpoly_adminapp.adapter.ClassTeacherAdapter;
import com.empire.sitpoly_adminapp.adapter.StudentAdapter;
import com.empire.sitpoly_adminapp.data.ClassTeacherData;
import com.empire.sitpoly_adminapp.data.StudentData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClassTeacherActivity extends AppCompatActivity {

    Query reference;
    RecyclerView recyclerView;
    ArrayList<ClassTeacherData> list;
    ClassTeacherAdapter adapter;
    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teacher);

        getSupportActionBar().setTitle("Class Teachers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        recyclerView = findViewById(R.id.class_teacher_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        loadingDialog.show();
        reference = FirebaseDatabase.getInstance().getReference("Students");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<ClassTeacherData>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ClassTeacherData classTeacherData = dataSnapshot.getValue(ClassTeacherData.class);
                    list.add(classTeacherData);
                }

                adapter = new ClassTeacherAdapter(ClassTeacherActivity.this,list);
                recyclerView.setAdapter(adapter);
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ClassTeacherActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
//        return super.onCreateOptionsMenu(menu);
//    }
//
//
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}