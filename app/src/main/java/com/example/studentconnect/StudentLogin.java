package com.example.studentconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StudentLogin extends AppCompatActivity {
    TextInputLayout til_email,til_password;
    EditText txt_email,txt_password;
    Button loginBtn,registerBtn;
    TextView forgotPassword;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_student_login);

        til_email=(TextInputLayout) findViewById(R.id.til_email);
        til_password=(TextInputLayout) findViewById(R.id.til_password);
        txt_email=(EditText) findViewById(R.id.stud_email);
        txt_password=(EditText) findViewById(R.id.stud_password);
        forgotPassword=(TextView) findViewById(R.id.forgotpass);
        loginBtn=(Button) findViewById(R.id.loginBtn);
        registerBtn=(Button) findViewById(R.id.registerBtn);

        mAuth=FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginStudent();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentLogin.this,StudentRegistration.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentLogin.this,ForgotPassword.class));
            }
        });
    }

    private void loginStudent() {
        String stud_email=txt_email.getText().toString().trim();
        String stud_password=txt_password.getText().toString().trim();
        final ProgressDialog progressDialog=new ProgressDialog(this);
        //progressDialog.setTitle("Logging In");
        progressDialog.show();

        if(stud_email.isEmpty()){
            til_email.setError("Email is required");
            til_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(stud_email).matches()){
            til_email.setError("Invalid email address");
            til_email.requestFocus();
            return;
        }
        if(stud_password.isEmpty()){
            til_password.setError("Password is required");
            til_password.requestFocus();
            return;
        }
        if(stud_password.length()<6){
            til_password.setError("Password is too short");
            til_password.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(stud_email,stud_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.setMessage("Please Wait");
                            startActivity(new Intent(StudentLogin.this, StudentDashboard.class));
                            finish();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(StudentLogin.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}