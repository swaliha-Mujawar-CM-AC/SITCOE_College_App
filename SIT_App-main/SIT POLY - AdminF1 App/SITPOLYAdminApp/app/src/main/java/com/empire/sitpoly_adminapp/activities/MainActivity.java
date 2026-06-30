package com.empire.sitpoly_adminapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.empire.sitpoly_adminapp.R;
import com.empire.sitpoly_adminapp.faculty.UpdateFacultyActivity;
import com.empire.sitpoly_adminapp.notice.DeleteNoticeActivity;
import com.empire.sitpoly_adminapp.notice.UploadNoticeActivity;
import com.empire.sitpoly_adminapp.privateData.UpNoticeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button uploadNotice, uploadImage,uploadEBook, updateFaculty,deleteNotice, studentsInfo, privateNotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadNotice = findViewById(R.id.uploadNotice);
        uploadImage = findViewById(R.id.uploadImage);
        uploadEBook = findViewById(R.id.uploadEBook);
        updateFaculty = findViewById(R.id.updateFaculty);
        deleteNotice = findViewById(R.id.deleteNotice);
        studentsInfo = findViewById(R.id.studentsInfo);
        privateNotice = findViewById(R.id.createStudent);

        uploadNotice.setOnClickListener(this);
        uploadImage.setOnClickListener(this);
        uploadEBook.setOnClickListener(this);
        updateFaculty.setOnClickListener(this);
        deleteNotice.setOnClickListener(this);
        studentsInfo.setOnClickListener(this);
        privateNotice.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.uploadNotice:
                startActivity(new Intent(MainActivity.this, UploadNoticeActivity.class));
               break;
            case R.id.createStudent:
                startActivity(new Intent(MainActivity.this, UpNoticeActivity.class));
                break;
            case R.id.uploadImage:
                startActivity(new Intent(MainActivity.this, UploadImageActivity.class));
                break;
            case R.id.uploadEBook:
                startActivity(new Intent(MainActivity.this, NotesActivity.class));
                break;
            case R.id.updateFaculty:
                startActivity(new Intent(MainActivity.this, UpdateFacultyActivity.class));
                break;
            case R.id.deleteNotice:
                startActivity(new Intent(MainActivity.this, DeleteNoticeActivity.class));
                break;
            case R.id.studentsInfo:
                startActivity(new Intent(MainActivity.this, ClassTeacherActivity.class));
                break;
        }

    }
}