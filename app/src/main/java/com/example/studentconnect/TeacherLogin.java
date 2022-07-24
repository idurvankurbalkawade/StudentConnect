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

public class TeacherLogin extends AppCompatActivity {
    TextInputLayout til_email,til_password;
    EditText txt_email,txt_password;
    Button loginBtn,registerBtn;
    //ProgressBar progressBar;
    TextView forgotPass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_teacher_login);

        til_email=(TextInputLayout) findViewById(R.id.til_email);
        til_password=(TextInputLayout) findViewById(R.id.til_password);
        txt_email=(EditText) findViewById(R.id.teacher_email);
        txt_password=(EditText) findViewById(R.id.teacher_password);
        loginBtn=(Button) findViewById(R.id.loginBtn);
        registerBtn=(Button) findViewById(R.id.registerBtn);
        //progressBar=(ProgressBar) findViewById(R.id.progressBar);
        forgotPass=(TextView) findViewById(R.id.forgotpass);

        mAuth=FirebaseAuth.getInstance();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginTeacher();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherLogin.this,TeacherRegistration.class));
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherLogin.this,ForgotPassword.class));
            }
        });
    }

    private void loginTeacher() {
        String teacher_email=txt_email.getText().toString().trim();
        String teacher_password=txt_password.getText().toString().trim();
        final ProgressDialog progressDialog=new ProgressDialog(this);

        if(teacher_email.isEmpty()){
            til_email.setError("Email is required");
            til_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(teacher_email).matches()){
            til_email.setError("Email is required");
            til_email.requestFocus();
            return;
        }
        if(teacher_password.isEmpty()){
            til_password.setError("Password is required");
            til_password.requestFocus();
            return;
        }
        if(teacher_password.length()<6){
            til_password.setError("Password is too short");
            til_password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(teacher_email,teacher_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Logged In Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.setMessage("Please Wait");
                            startActivity(new Intent(TeacherLogin.this,TeacherDashboard.class));
                            finish();

                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(TeacherLogin.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}