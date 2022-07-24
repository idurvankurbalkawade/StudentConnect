package com.example.studentconnect;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {
    EditText aemail,apassword;
    Button adminlogin;
    ProgressBar progress;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_admin_login);

        aemail=findViewById(R.id.adminemail);
        apassword=findViewById(R.id.adminpassword);
        progress=findViewById(R.id.adminprogress);
        fAuth=FirebaseAuth.getInstance();
        adminlogin=findViewById(R.id.loginbutton);


        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=aemail.getText().toString().trim();
                String pass=apassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    aemail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(pass)){
                    apassword.setError("Password is required");
                    return;
                }

                if (pass.length()<6){
                    apassword.setError("logged in to wrond panel");
                    return;
                }

                progress.setVisibility(View.GONE);

                //authenticate user
                fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AdminLogin.this, "Admin logged In", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));

                        }else{
                            Toast.makeText( AdminLogin.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);

                        }

                    }
                });



            }
        });
    }

}