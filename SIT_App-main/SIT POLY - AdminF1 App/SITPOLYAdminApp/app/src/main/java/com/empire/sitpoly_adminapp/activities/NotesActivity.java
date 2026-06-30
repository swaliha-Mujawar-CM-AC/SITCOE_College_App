package com.empire.sitpoly_adminapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.empire.sitpoly_adminapp.R;
import com.empire.sitpoly_adminapp.privateData.UpNoticeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class
NotesActivity extends AppCompatActivity {

    private Button uploadPdf;
    private ImageView addPdf;
    private EditText pdfTitle;
    private TextView pdfName;

    private final int REQ = 1;
    private Uri pdfData;
    private ProgressDialog pd;
    private Spinner selectClassTeacher;
    private String pdfName1, title, classTeacher;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        addPdf = findViewById(R.id.add_pdf);
        uploadPdf = findViewById(R.id.uploadPdf);
        pdfTitle = findViewById(R.id.pdf_title);
        pdfName = findViewById(R.id.pdfName);
        selectClassTeacher = findViewById(R.id.select_class_teacher_sp_notes);

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

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        uploadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = pdfTitle.getText().toString();
                if (title.isEmpty()){
                    pdfTitle.setError("Empty");
                    pdfTitle.requestFocus();
                }else if (pdfData == null){
                    Toast.makeText(NotesActivity.this, "Please Upload PDf", Toast.LENGTH_SHORT).show();
                }else if (classTeacher.equals("Select Class Teacher")) {
                    Toast.makeText(NotesActivity.this, "Please Select Class Teacher", Toast.LENGTH_SHORT).show();
                } else {
                    uploadPdf();
                }

            }
        });
    }

    private void uploadPdf() {
        pd.setTitle("Please wait...");
        pd.setMessage("Uploading PDF file...");
        pd.show();
        StorageReference reference = storageReference.child("pdf/"+pdfName1+"-"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfData)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri =  uriTask.getResult();
                        uploadData(String.valueOf(uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(NotesActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData(String downloadUrl) {
        String uniqueKey = databaseReference.child("pdf").push().getKey();

        HashMap data = new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downloadUrl);

        databaseReference.child("pdf").child(classTeacher).child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(NotesActivity.this, "pdf uploaded successfully", Toast.LENGTH_SHORT).show();
                pdfTitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(NotesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {

        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF file"),REQ);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ && resultCode == RESULT_OK){
            pdfData = data.getData();

            if (pdfData.toString().startsWith("content://")){

                try {
                    Cursor cursor = null;
                    cursor = NotesActivity.this.getContentResolver().query(pdfData,null,null,null,null);
                    if (cursor != null && cursor.moveToFirst()){
                        pdfName1 = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if (pdfData.toString().startsWith("file://")){
                pdfName1 = new File(pdfData.toString()).getName();
            }

            pdfName.setText(pdfName1);
            
        }
    }
}