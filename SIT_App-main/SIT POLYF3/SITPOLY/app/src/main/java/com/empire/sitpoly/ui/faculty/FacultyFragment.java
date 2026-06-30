package com.empire.sitpoly.ui.faculty;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.empire.sitpoly.R;
import com.empire.sitpoly.adapter.TeacherAdapter;
import com.empire.sitpoly.data.TeacherData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FacultyFragment extends Fragment {

    private RecyclerView csDept, meDept, itDept, civilDept, eleDept;
    private LinearLayout csNoData, meNoData, itNoData, civilNoData, eleNoData;
    private List<TeacherData> csList, meList, itList, civilList, eleList;

    private DatabaseReference reference, dbRef;
    private TeacherAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);

        csDept = view.findViewById(R.id.cs_department);
        meDept = view.findViewById(R.id.mechanical_department);
        itDept = view.findViewById(R.id.it_department);
        civilDept = view.findViewById(R.id.civil_department);
        eleDept = view.findViewById(R.id.electrical_department);

        csNoData = view.findViewById(R.id.cs_no_data);
        meNoData = view.findViewById(R.id.mechanical_no_data);
        itNoData = view.findViewById(R.id.it_no_data);
        civilNoData = view.findViewById(R.id.civil_no_data);
        eleNoData = view.findViewById(R.id.electrical_no_data);



        reference = FirebaseDatabase.getInstance().getReference().child("teacher");

        csDepartment();
        meDepartment();
        itDepartment();
        civilDepartment();
        eleDepartment();

        return view;
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
                    eleDept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(eleList,getContext());
                    eleDept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    itDept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(itList,getContext());
                    itDept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    civilDept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(civilList,getContext());
                    civilDept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    meDept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(meList,getContext());
                    meDept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    csDept.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(csList,getContext());
                    csDept.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    }
