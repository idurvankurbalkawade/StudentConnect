package com.example.studentconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
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

public class TeacherProfile extends AppCompatActivity implements View.OnClickListener {
    private TextView teacher_name,teacher_email,teacher_subject,welcome_user;
    private FirebaseUser user_teacher;
    private DatabaseReference reference;
    private String userID;
    private Button changeProfile,forgotPassword,btnBack;
    private ImageView profileImage;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_teacher_profile);

        teacher_name=(TextView) findViewById(R.id.txt_full_name);
        teacher_email=(TextView) findViewById(R.id.txt_email);
        teacher_subject=(TextView) findViewById(R.id.txt_class);
        welcome_user=(TextView) findViewById(R.id.welcome_user);
        changeProfile=(Button) findViewById(R.id.btnChangeProfile);
        forgotPassword=(Button) findViewById(R.id.btnForgotPassword);
        btnBack=(Button)findViewById(R.id.backBtn);
        profileImage=(ImageView) findViewById(R.id.profileImage);

        mAuth=FirebaseAuth.getInstance();

        changeProfile.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);

        //Retrieve Uploaded Image from Firebase Storage
        StorageReference fileProfile= FirebaseStorage.getInstance().getReference().child("Teacher/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        try {
            final File localFile=File.createTempFile("profile","jpg");
            fileProfile.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            profileImage.setImageBitmap(bitmap);
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        user_teacher=FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Teachers");
        userID=user_teacher.getUid();



        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher teacher=snapshot.getValue(Teacher.class);

                if(teacher!=null)
                {
                    String welcome="Welcome "+teacher.fullName;
                    String t_name=teacher.fullName;
                    String t_email=teacher.email;
                    String t_subject=teacher.subject;

                    welcome_user.setText(welcome);
                    teacher_name.setText(t_name);
                    teacher_email.setText(t_email);
                    teacher_subject.setText(t_subject);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error in fetching data!!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnChangeProfile:
                startActivity(new Intent(TeacherProfile.this,EditTeacherProfile.class));
                //finish();
                break;

            case R.id.btnForgotPassword:
                startActivity(new Intent(TeacherProfile.this,ForgotPassword.class));
                //finish();
                break;
        }
    }
    public void backBtn(View view){
        finishAndRemoveTask();
        Intent intent=new Intent(getApplicationContext(),TeacherDashboard.class);
        finishAffinity();
        startActivity(intent);
    }
}