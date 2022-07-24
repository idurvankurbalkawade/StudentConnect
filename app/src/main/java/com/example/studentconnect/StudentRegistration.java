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

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class StudentRegistration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner sub;
    String Class1[]={"A","B"};
    TextInputLayout t1,t2,t3,t4;
    EditText txt_name,txt_email,txt_password,txt_confirmPassword;
    Button registerBtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_student_registration);

        t1=(TextInputLayout) findViewById(R.id.til1);
        t2=(TextInputLayout) findViewById(R.id.til2);
        t3=(TextInputLayout) findViewById(R.id.til3);
        t4=(TextInputLayout) findViewById(R.id.til4);

        mAuth=FirebaseAuth.getInstance();

        txt_name=(EditText)findViewById(R.id.txt_Name);
        txt_email=(EditText)findViewById(R.id.txt_Email);
        txt_password=(EditText)findViewById(R.id.txt_Password);
        txt_confirmPassword=(EditText)findViewById(R.id.txt_confirmPassword);

        registerBtn=(Button)findViewById(R.id.registerBtn);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);

        sub = (Spinner) findViewById(R.id.studClass);
        sub.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.spinner_style,Class1);
        aa.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        sub.setAdapter(aa);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStudent();
            }
        });

    }

    private void registerStudent() {
        String stud_name=txt_name.getText().toString().trim();
        String stud_email=txt_email.getText().toString().trim();
        String password=txt_password.getText().toString().trim();
        String confirmPassword=txt_confirmPassword.getText().toString().trim();
        String stud_Class=sub.getSelectedItem().toString();

        if(stud_name.isEmpty()){
            t1.setError("Name is required");
            t1.requestFocus();
            return;
        }

        if(!stud_name.matches("[a-zA-Z ]+")){
            t1.setError("Invalid Name");
            t1.requestFocus();
            return;
        }

        if(stud_email.isEmpty()){
            t2.setError("Email is required");
            t2.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(stud_email).matches()){
            t2.setError("Invalid Email Address");
            t2.requestFocus();
            return;
        }

        if(password.isEmpty()){
            t3.setError("Password is required");
            t3.requestFocus();
            return;
        }

        if(password.length()<6){
            t3.setError("Password is too short");
            t3.requestFocus();
            return;
        }

        if(confirmPassword.isEmpty()){
            t4.setError("Password is required");
            t4.requestFocus();
            return;
        }
        if(!password.equals(confirmPassword)){
            Toast.makeText(StudentRegistration.this,"Password is incorrect",Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(stud_email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Student student=new Student(stud_name,stud_email,stud_Class);

                            FirebaseDatabase.getInstance().getReference("Students")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(StudentRegistration.this, "Student has been registered successfully", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else{
                                        Toast.makeText(StudentRegistration.this, "Failed to register!!Try Again", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(StudentRegistration.this,"Failed to register!!Try Again",Toast.LENGTH_SHORT).show();
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