package com.empire.sitpoly_adminapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.empire.sitpoly_adminapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterStudentActivity extends AppCompatActivity {

    private EditText userId,password;
    private Button registerBtn;
    private Spinner selectDept, selectYear;
    private String dept, year;

    private ProgressDialog pd;

    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        userId = findViewById(R.id.student_userId_ed);
        password = findViewById(R.id.student_password_ed);
        registerBtn = findViewById(R.id.resister_student_btn);
        selectDept = findViewById(R.id.student_department_sp);
        selectYear = findViewById(R.id.student_year_sp);
        pd = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Students");



        String[] departments = new String[]{"Select Department", "Computer Science",
                "Mechanical", "Civil","Information Technology","Electrical"};

        String[] years = new String[]{"Select Year", "first year",
                "Second Year", "Third Year"};

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

        if (email.isEmpty()){
            userId.setError("Required Field");
            userId.requestFocus();
        }

        if (pass.isEmpty()){
            password.setError("Required Field");
            password.requestFocus();
        }else if (dept.equals("Select Department")){
            Toast.makeText(this, "Please select department!", Toast.LENGTH_SHORT).show();
        }else if (year.equals("Select Year")) {
            Toast.makeText(this, "Please select year!", Toast.LENGTH_SHORT).show();
        }else {
            pd.setTitle("Please Wait");
            pd.setMessage("Creating new Student...");
            pd.show();
            RegisterStudent(email,pass,dept,year);
        }

    }

    private void RegisterStudent(String email, String pass, String dept, String year) {

        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(RegisterStudentActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {




                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("department",dept);
                            hashMap.put("year",year);

                            reference.child(userid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        pd.dismiss();
                                        Toast.makeText(RegisterStudentActivity.this, "Student created successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else {
                            Toast.makeText(RegisterStudentActivity.this, "You can't register with these email", Toast.LENGTH_SHORT).show();
                        }



                    }
                });

    }
}