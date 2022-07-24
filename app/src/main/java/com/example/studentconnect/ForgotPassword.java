package com.example.studentconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private TextInputLayout textInputLayout;
    private EditText emailAddress;
    private Button resetBtn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_forgot_password);

        textInputLayout =(TextInputLayout) findViewById(R.id.til);
        emailAddress=(EditText) findViewById(R.id.emailID);
        resetBtn=(Button) findViewById(R.id.resetBtn);
        mAuth=FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email_Address=emailAddress.getText().toString().trim();

        if(email_Address.isEmpty())
        {
            textInputLayout.setError("Please provide email address");
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_Address).matches())
        {
            textInputLayout.setError("Invalid Email Address");
        }

        mAuth.sendPasswordResetEmail(email_Address).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Reset Link has been sent to your registered email address", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please try again!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}