package com.empire.sitpoly_adminapp.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.empire.sitpoly_adminapp.R;
import com.empire.sitpoly_adminapp.adapter.TeacherAdapter;
import com.empire.sitpoly_adminapp.data.TeacherData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateFacultyActivity extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView csDept, meDept, itDept, civilDept, eleDept;
    private LinearLayout csNoData, meNoData, itNoData, civilNoData, eleNoData;
    private List<TeacherData> csList, meList, itList, civilList, eleList;

    private DatabaseReference reference, dbRef;
    private TeacherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        csDept = findViewById(R.id.cs_department);
        meDept = findViewById(R.id.mechanical_department);
        itDept = findViewById(R.id.it_department);
        civilDept = findViewById(R.id.civil_department);
        eleDept = findViewById(R.id.electrical_department);

        csNoData = findViewById(R.id.cs_no_data);
        meNoData = findViewById(R.id.mechanical_no_data);
        itNoData = findViewById(R.id.it_no_data);
        civilNoData = findViewById(R.id.civil_no_data);
        eleNoData = findViewById(R.id.electrical_no_data);

        fab = findViewById(R.id.fab);

        reference = FirebaseDatabase.getInstance().getReference().child("teacher");

        csDepartment();
        meDepartment();
        itDepartment();
        civilDepartment();
        eleDepartment();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateFacultyActivity.this,AddTeacherActivity.class));
            }
        });
    }

    private void eleDepartment() {
        dbRef = reference.child("Electrical");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eleList = new ArrayList<>();
                if (!snapshot.exists()){
                    eleNoData.setVisibility(View.VISIBLE);
                    eleDept.setVisibility(View.GONE);
                }else {
                    eleNoData.setVisibility(View.GONE);
                    eleDept.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        eleList.add(data);
                    }
                    eleDept.setHasFixedSize(true);
                    eleDept.setLayoutManager(new LinearLayoutManager(UpdateFacultyActivity.this));
                    adapter = new TeacherAdapter(eleList,UpdateFacultyActivity.this,"Electrical");
                    eleDept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void itDepartment() {
        dbRef = reference.child("IT");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itList = new ArrayList<>();
                if (!snapshot.exists()){
                    itNoData.setVisibility(View.VISIBLE);
                    itDept.setVisibility(View.GONE);
                }else {
                    itNoData.setVisibility(View.GONE);
                    itDept.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        itList.add(data);
                    }
                    itDept.setHasFixedSize(true);
                    itDept.setLayoutManager(new LinearLayoutManager(UpdateFacultyActivity.this));
                    adapter = new TeacherAdapter(itList,UpdateFacultyActivity.this,"IT");
                    itDept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void civilDepartment() {
        dbRef = reference.child("Civil");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                civilList = new ArrayList<>();
                if (!snapshot.exists()){
                    civilNoData.setVisibility(View.VISIBLE);
                    civilDept.setVisibility(View.GONE);
                }else {
                    civilNoData.setVisibility(View.GONE);
                    civilDept.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        civilList.add(data);
                    }
                    civilDept.setHasFixedSize(true);
                    civilDept.setLayoutManager(new LinearLayoutManager(UpdateFacultyActivity.this));
                    adapter = new TeacherAdapter(civilList,UpdateFacultyActivity.this,"Civil");
                    civilDept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void meDepartment() {
        dbRef = reference.child("Mechanical");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meList = new ArrayList<>();
                if (!snapshot.exists()){
                    meNoData.setVisibility(View.VISIBLE);
                    meDept.setVisibility(View.GONE);
                }else {
                    meNoData.setVisibility(View.GONE);
                    meDept.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        meList.add(data);
                    }
                    meDept.setHasFixedSize(true);
                    meDept.setLayoutManager(new LinearLayoutManager(UpdateFacultyActivity.this));
                    adapter = new TeacherAdapter(meList,UpdateFacultyActivity.this,"Mechanical");
                    meDept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void csDepartment() {

        dbRef = reference.child("Computer Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                csList = new ArrayList<>();
                if (!snapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csDept.setVisibility(View.GONE);
                }else {
                    csNoData.setVisibility(View.GONE);
                    csDept.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data = snapshot1.getValue(TeacherData.class);
                        csList.add(data);
                    }
                    csDept.setHasFixedSize(true);
                    csDept.setLayoutManager(new LinearLayoutManager(UpdateFacultyActivity.this));
                    adapter = new TeacherAdapter(csList,UpdateFacultyActivity.this,"Computer Science");
                    csDept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFacultyActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}