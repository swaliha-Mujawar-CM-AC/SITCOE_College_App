
package com.empire.sitpoly.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.empire.sitpoly.R;
import com.empire.sitpoly.activities.LoginActivity;
import com.empire.sitpoly.activities.StatusActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText userId,password, name, enrollNo;
    private TextView registerBtn;
    private Spinner selectDept, selectYear, selectClassTeacher;
    private String dept, year, classTeacher;

    private ProgressDialog pd;
    FloatingActionButton fab;

    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;


    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        //redirect if user is not null
        if(firebaseUser != null)
        {
            startActivity(new Intent(RegisterActivity.this, StatusActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userId = findViewById(R.id.student_userId_ed);
        password = findViewById(R.id.student_password_ed);
        registerBtn = findViewById(R.id.resister_student_btn);
        selectDept = findViewById(R.id.student_department_sp);
        selectYear = findViewById(R.id.student_year_sp);
        selectClassTeacher = findViewById(R.id.student_class_teacher_sp);
        name = findViewById(R.id.student_name);
        enrollNo = findViewById(R.id.student_enrollment_no);
        pd = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Students");


        fab = findViewById(R.id.fab_register);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        String[] departments = new String[]{"Select Department", "Computer Science",
                "Mechanical", "Civil","Information Technology","Electrical"};

        String[] years = new String[]{"Select Year", "first year",
                "Second Year", "Third Year"};

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

        selectDept.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,departments));
        selectDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                dept = selectDept.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        selectYear.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,years));
        selectYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = selectYear.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

    }

    private void checkValidation() {

        String email = userId.getText().toString();
        String pass = password.getText().toString();
        String nm = name.getText().toString();
        String no = enrollNo.getText().toString();

        if (email.isEmpty()) {
            userId.setError("Required Field");
            userId.requestFocus();
        }else if (pass.isEmpty()){
            password.setError("Required Field");
            password.requestFocus();
        }else if (nm.isEmpty()){
            name.setError("Required Field");
            name.requestFocus();
        }else if(no.isEmpty()){
            enrollNo.setError("Required Field");
            enrollNo.requestFocus();
        }
        else if (dept.equals("Select Department")){
            Toast.makeText(this, "Please select department!", Toast.LENGTH_SHORT).show();
        }else if (year.equals("Select Year")) {
            Toast.makeText(this, "Please select year!", Toast.LENGTH_SHORT).show();
        } else if (classTeacher.equals("Select Class Teacher")){
            Toast.makeText(this, "Please Select Your Class Teacher!", Toast.LENGTH_SHORT).show();
        } else{
            pd.setTitle("Please Wait");
            pd.setMessage("Creating new Student...");
            pd.show();


            RegisterStudent(email,pass,dept,year,nm,no,classTeacher);
        }

    }



    private void RegisterStudent(String email, String pass, String dept, String year, String name, String enrollNo, String classTeacher) {

        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {




                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userid = firebaseUser.getUid();
                            String status = "false";

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("name",name);
                            hashMap.put("enrollNo",enrollNo);
                            hashMap.put("classTeacher",classTeacher);
                            hashMap.put("department",dept);
                            hashMap.put("verification",status);
                            hashMap.put("year",year);
                            hashMap.put("studentId",userid);

                            reference.child(classTeacher).child("students").child(userid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        Intent intent = new Intent(RegisterActivity.this,StatusActivity.class);
                                        intent.putExtra("classTeacherName", classTeacher);
                                        intent.putExtra("userId", userid);
                                        intent.putExtra("status",status);
                                        startActivity(intent);
                                        Toast.makeText(RegisterActivity.this, "Student created successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }else {
                            Toast.makeText(RegisterActivity.this, "You can't register with these email", Toast.LENGTH_SHORT).show();
                        }



                    }
                });

    }

}