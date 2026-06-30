package com.empire.sitpoly.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.empire.sitpoly.R;
import com.empire.sitpoly.adapter.EbookAdapter;
import com.empire.sitpoly.data.EbookData;
import com.empire.sitpoly.ui.gallery.PrivateNoticeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PdfActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private List<EbookData> list;
    private  EbookAdapter adapter;

    String teacherName;
    private FirebaseUser user;
    private TextView tv;
private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        toolbar = findViewById(R.id.toolbar_pdf);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Study Material");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = FirebaseAuth.getInstance().getCurrentUser();


        if (!user.isAnonymous()){

            SharedPreferences getValue1 = getSharedPreferences("childValues",MODE_PRIVATE);
            teacherName = getValue1.getString("classTeacherName","s");

            recyclerView = findViewById(R.id.pdf_recycler);

            reference = FirebaseDatabase.getInstance().getReference().child("pdf");

            getData();
        }else {
            tv = findViewById(R.id.text_pdf);
            tv.setText("Permission Detained! To view this content, Login as a Student");
        }

    }

    private void getData() {
        reference.child(teacherName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    EbookData data = snapshot1.getValue(EbookData.class);
                    list.add(data);
                }

                adapter = new EbookAdapter(PdfActivity.this,list);
                recyclerView.setLayoutManager(new LinearLayoutManager(PdfActivity.this));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PdfActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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