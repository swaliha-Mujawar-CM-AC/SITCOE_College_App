package com.empire.sitpoly_adminapp.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.empire.sitpoly_adminapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateTeacherActivity extends AppCompatActivity {

    private ImageView teacherImage;
    private EditText teacherName, teacherEmail, teacherPost;
    private Button updateBtn , deleteBtn;

    private String name, email, image, post,category,uniqueKey, downloadUrl = "";
    private final int REQ = 1;
    private Bitmap bitmap = null;

    private StorageReference storageReference;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        teacherImage = findViewById(R.id.update_teacher_image);
        teacherName = findViewById(R.id.update_teacher_name);
        teacherEmail = findViewById(R.id.update_teacher_email);
        teacherPost = findViewById(R.id.update_teacher_post);
        updateBtn = findViewById(R.id.update_teacher_btn);
        deleteBtn =findViewById(R.id.delete_teacher_btn);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        image = getIntent().getStringExtra("image");
        post = getIntent().getStringExtra("post");
        try {
            Picasso.get().load(image).into(teacherImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        reference = FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference = FirebaseStorage.getInstance().getReference();

        uniqueKey = getIntent().getStringExtra("key");
        category =  getIntent().getStringExtra("category");

       teacherEmail.setText(email);
       teacherName.setText(name);
       teacherPost.setText(post);

       teacherImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               openGallery();
           }
       });
       
       updateBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               name = teacherName.getText().toString();
               email = teacherEmail.getText().toString();
               post = teacherPost.getText().toString();

               checkValidation();
           }
       });
       
       deleteBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               deleteData();
           }
       });
    }

    private void deleteData() {

        reference.child(category).child(uniqueKey).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UpdateTeacherActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateTeacherActivity.this,UpdateFacultyActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacherActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkValidation() {

        if (name.isEmpty()){
            teacherName.setError("Required Field");
            teacherName.requestFocus();
        }else if (email.isEmpty()){
            teacherEmail.setError("Required Field");
            teacherEmail.requestFocus();
        }else if (post.isEmpty()){
            teacherPost.setError("Required Field");
            teacherPost.requestFocus();
        }else if (bitmap == null){
            updateData(image);
        }else {
            uploadImage();
        }


    }

    private void updateData(String s) {

        HashMap hashMap = new HashMap();
        hashMap.put("name",name);
        hashMap.put("email",email);
        hashMap.put("post",post);
        hashMap.put("image",s);



        reference.child(category).child(uniqueKey).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateTeacherActivity.this, "Teacher updated successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateTeacherActivity.this,UpdateFacultyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacherActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadImage() {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] finalImage = baos.toByteArray();

        final StorageReference filePath;
        filePath = storageReference.child("Teachers").child(finalImage+"jpg");
        final UploadTask uploadTask = filePath.putBytes(finalImage);

        uploadTask.addOnCompleteListener(UpdateTeacherActivity.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    updateData(downloadUrl);
                                }
                            });
                        }
                    });
                }else {

                    Toast.makeText(UpdateTeacherActivity.this,"Something went wrong!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            teacherImage.setImageBitmap(bitmap);
        }
    }
}