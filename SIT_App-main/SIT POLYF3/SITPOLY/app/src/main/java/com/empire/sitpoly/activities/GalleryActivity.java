package com.empire.sitpoly.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.empire.sitpoly.R;
import com.empire.sitpoly.adapter.GalleryAdapter;
import com.empire.sitpoly.adapter.NoticeAdapter;
import com.empire.sitpoly.data.NoticeData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private NoticeAdapter noticeAdapter;

    private DatabaseReference reference;
    private FirebaseUser firebaseUser;
    private Toolbar toolbar;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        toolbar = findViewById(R.id.toolbar_gallery);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Departmental Notice");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        assert firebaseUser != null;
        if (!firebaseUser.isAnonymous()){

            recyclerView = findViewById(R.id.notice_recyclerview_private);
            progressBar = findViewById(R.id.notice_progressbar_private);

            SharedPreferences getValue1 = getSharedPreferences("childValues",MODE_PRIVATE);
            String teacherName = getValue1.getString("classTeacherName","s");


            reference = FirebaseDatabase.getInstance().getReference().child("privateNotice").child(teacherName);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);


            getNotice();
            progressBar.setVisibility(View.VISIBLE);


        }else {
                tv = findViewById(R.id.text_gallery);
                tv.setText("Permission Detained! To view this content, Login as a Student");
        }








    }

    private void getNotice() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list = new ArrayList<>();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    NoticeData data = snapshot1.getValue(NoticeData.class);

                    // if ( firebaseUser.getUid().contains(data.getKey())){
                    list.add(0,data);

                    //}

                }

                noticeAdapter = new NoticeAdapter(GalleryActivity.this,list);
                noticeAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setAdapter(noticeAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(GalleryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);



    }
}