package com.example.studentconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner sub;
    String subject[]={"C","C++","Java","Python"};
    TextInputLayout txt1,txt2,txt3,txt4;
    EditText txt_Name,txt_email,txt_password,txt_confirmPassword;
    Button registerBtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_teacher_registration);

        txt1=(TextInputLayout) findViewById(R.id.txt1);
        txt2=(TextInputLayout) findViewById(R.id.txt2);
        txt3=(TextInputLayout) findViewById(R.id.txt3);
        txt4=(TextInputLayout) findViewById(R.id.txt4);

        mAuth=FirebaseAuth.getInstance();

        txt_Name=(EditText) findViewById(R.id.t_name);
        txt_email=(EditText)findViewById(R.id.email);
        txt_password=(EditText) findViewById(R.id.pass);
        txt_confirmPassword=(EditText) findViewById(R.id.cpass);
        registerBtn=(Button) findViewById(R.id.register);

        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        sub = (Spinner) findViewById(R.id.subjects);
        sub.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_style,subject);
        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        sub.setAdapter(aa);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerTeacher();
            }
        });
    }

    private void registerTeacher() {
        String teacher_name=txt_Name.getText().toString().trim();
        String teacher_email=txt_email.getText().toString().trim();
        String password=txt_password.getText().toString().trim();
        String confirm_password=txt_confirmPassword.getText().toString().trim();
        String teacher_subject=sub.getSelectedItem().toString();

        if(teacher_name.isEmpty()){
            txt1.setError("Name is required");
            txt1.requestFocus();
            return;
        }
        if(!teacher_name.matches("[a-zA-Z ]+")){
            txt1.setError("Invalid Name");
            txt1.requestFocus();
            return;
        }
        if(teacher_email.isEmpty()){
            txt2.setError("Email is required");
            txt2.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(teacher_email).matches()){
            txt2.setError("Invalid Email Address");
            txt2.requestFocus();
            return;
        }

        if(password.isEmpty()){
            txt3.setError("Password is required");
            txt3.requestFocus();
            return;
        }

        if(password.length()<6){
            txt3.setError("Password is too short");
            txt3.requestFocus();
            return;
        }

        if(confirm_password.isEmpty()){
            txt4.setError("Password is required");
            txt4.requestFocus();
            return;
        }
        if(!password.equals(confirm_password)){
            Toast.makeText(TeacherRegistration.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(teacher_email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Teacher teacher=new Teacher(teacher_name,teacher_email,teacher_subject);

                            FirebaseDatabase.getInstance().getReference("Teachers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(TeacherRegistration.this,"Teacher has been registered successfully",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else{
                                        Toast.makeText(TeacherRegistration.this,"Failed to register",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(TeacherRegistration.this,"Failed to register",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(getApplicationContext(),sub.getSelectedItem().toString() , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}