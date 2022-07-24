package com.example.studentconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class StudentProfile extends AppCompatActivity {
    private TextView fullName,email,stud_class,welcome_user;
    private FirebaseUser user_student;
    private DatabaseReference reference;
    private String userID;
    private Button changeProfile,forgotPassword,btnBack;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_student_profile);

        fullName=(TextView) findViewById(R.id.txt_full_name);
        email=(TextView) findViewById(R.id.txt_email);
        stud_class=(TextView) findViewById(R.id.txt_class);
        welcome_user=(TextView) findViewById(R.id.welcome_user);
        changeProfile=(Button) findViewById(R.id.btnChangeProfile);
        forgotPassword=(Button) findViewById(R.id.btnForgotPassword);
        btnBack=(Button)findViewById(R.id.backBtn);
        ImageView profile_Image=(ImageView) findViewById(R.id.profileImage);

        user_student=FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Students");
        userID=user_student.getUid();
        mAuth=FirebaseAuth.getInstance();

        storageReference= FirebaseStorage.getInstance().getReference().child("Student/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        try {
            final File localFile=File.createTempFile("profile","jpg");
            storageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            profile_Image.setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }


        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfile.this,EditStudentProfile.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentProfile.this,ForgotPassword.class));
            }
        });


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student=snapshot.getValue(Student.class);

                if(student!=null)
                {
                    String welcome="Welcome "+student.studentName;
                    String student_Name=student.studentName;
                    String student_email=student.email;
                    String student_class=student.studentClass;

                    welcome_user.setText(welcome);
                    fullName.setText(student_Name);
                    email.setText(student_email);
                    stud_class.setText(student_class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void backBtn(View view){
        finishAndRemoveTask();
        Intent intent=new Intent(getApplicationContext(),StudentDashboard.class);
        finishAffinity();
        startActivity(intent);
    }
}