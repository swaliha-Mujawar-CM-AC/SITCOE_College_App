package com.empire.sitpoly.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.empire.sitpoly.R;
import com.empire.sitpoly.authentication.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button loginBtn, guestLoginBtn;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;

    private ProgressDialog pd;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_email_ed);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_btn);
        guestLoginBtn = findViewById(R.id.guest_login_btn);
        fab = findViewById(R.id.fab_login);
        pd = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

        guestLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setTitle("Please Wait!");
                pd.setMessage("Login In...");
                pd.show();
                loginGuest();
            }
        });

    }

    private void loginGuest() {

        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            pd.dismiss();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();


                        } else {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show(); // If sign in fails, display a message to the user.
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkValidation() {

         String str_mail = email.getText().toString();
         String str_pass = password.getText().toString();

        if (str_mail.isEmpty()){
            email.setError("Required Field");;
            email.requestFocus();
        }else if (str_pass.isEmpty()){
            password.setError("Required Field");
            password.requestFocus();
        }else {
            LoginStudent(str_mail,str_pass);
        }
    }

    private void LoginStudent( String str_mail, String str_pass) {

        firebaseAuth.signInWithEmailAndPassword(str_mail,str_pass)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            pd.setTitle("Please Wait!");
                            pd.setMessage("Login In...");
                            pd.show();

                            reference = FirebaseDatabase.getInstance().getReference().child("Students")
                                    .child(firebaseAuth.getCurrentUser().getUid());

                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    pd.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    pd.dismiss();
                                    Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else
                        {
                            pd.dismiss();
                            Toast.makeText(LoginActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
}