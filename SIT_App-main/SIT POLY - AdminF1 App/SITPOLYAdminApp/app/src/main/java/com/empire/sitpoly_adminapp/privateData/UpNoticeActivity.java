package com.empire.sitpoly_adminapp.privateData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.empire.sitpoly_adminapp.R;
import com.empire.sitpoly_adminapp.data.NoticeData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpNoticeActivity extends AppCompatActivity {

    ImageView addImage, imagePreview;
    private EditText noticeTitle;

    private final int REQ = 1;
    private Bitmap bitmap;

    private DatabaseReference reference, dbRef;
    private StorageReference storageReference;

    String downloadUrl= "";
    String classTeacher;

    private ProgressDialog pd;
    private Spinner selectClassTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_notice);

        addImage = findViewById(R.id.add_notice_image_private);
        imagePreview = findViewById(R.id.image_preview_private);
        noticeTitle = findViewById(R.id.notice_desc_private);
        Button uploadNoticeBtn = findViewById(R.id.upload_notice_btn_private);
        selectClassTeacher = findViewById(R.id.select_class_teacher_sp);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        String[] classTeachers = new String[]{"Select Class Teacher", "MS U L Kokate", "MS P R Chougule"};

        selectClassTeacher.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,classTeachers));
        selectClassTeacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classTeacher = selectClassTeacher.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        pd = new ProgressDialog(this);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        uploadNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noticeTitle.getText().toString().isEmpty()){
                    noticeTitle.setError("Please Enter Description about Notice");
                    noticeTitle.requestFocus();
                }else if (classTeacher.equals("Select Class Teacher")){
                    Toast.makeText(UpNoticeActivity.this, "Please Select Class Teacher", Toast.LENGTH_SHORT).show();
                }else if (bitmap == null){
                    uploadData();
                }else {
                    uploadImage();
                }
            }
        });

    }

    private void uploadData() {

        dbRef = reference.child("privateNotice").child(classTeacher);
        final String uniqueKey = dbRef.push().getKey();
        String title = noticeTitle.getText().toString();

        Calendar calForDate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date = currentDate.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForTime.getTime());

        NoticeData noticeData = new NoticeData(title,downloadUrl,date,time,uniqueKey);

        dbRef.child(uniqueKey).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                pd.dismiss();
                Toast.makeText(UpNoticeActivity.this, "Notice Uploaded Successfully!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                String error = e.getCause().toString();
                Toast.makeText(UpNoticeActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage() {

        pd.setMessage("Uploading");
        pd.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage = baos.toByteArray();

        final StorageReference filePath;
        filePath = storageReference.child("privateNotice").child(finalImage+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);

        uploadTask.addOnCompleteListener(UpNoticeActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(UpNoticeActivity.this,"Something went wrong!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK){
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagePreview.setImageBitmap(bitmap);
        }
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }


}