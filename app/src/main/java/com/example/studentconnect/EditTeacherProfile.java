package com.example.studentconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
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
import java.util.HashMap;

public class EditTeacherProfile extends AppCompatActivity {
    private EditText txt_teacher_name,txt_teacher_email,txt_teacher_subject;
    private Button saveBtn;
    private ImageView profile_Image;
    private FirebaseUser user_teacher;
    private DatabaseReference reference;
    private String userID;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_teacher_profile);

        txt_teacher_name=(EditText) findViewById(R.id.txt_full_name);
        txt_teacher_email=(EditText) findViewById(R.id.txt_email);
        txt_teacher_subject=(EditText) findViewById(R.id.txt_class);
        saveBtn=(Button) findViewById(R.id.saveBtn);
        profile_Image=(ImageView) findViewById(R.id.profileImage);
        //storageReference= FirebaseStorage.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();

        profile_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });

        user_teacher= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Teachers");
        userID=user_teacher.getUid();

        //Retrieve Uploaded Image from Firebase Storage
        StorageReference fileProfile= FirebaseStorage.getInstance().getReference().child("Teacher/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        try {
            final File localFile=File.createTempFile("profile","jpg");
            fileProfile.getFile(localFile)
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


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher teacher=snapshot.getValue(Teacher.class);

                if(teacher!=null)
                {
                    String teacher_name=teacher.fullName;
                    String teacher_email=teacher.email;
                    String teacher_subject=teacher.subject;

                    //welcome_user.setText(welcome);
                    txt_teacher_name.setText(teacher_name);
                    txt_teacher_email.setText(teacher_email);
                    txt_teacher_subject.setText(teacher_subject);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t_name=txt_teacher_name.getText().toString();
                String t_email=txt_teacher_email.getText().toString();
                String t_subject=txt_teacher_subject.getText().toString();

                if(t_name.isEmpty()|| t_email.isEmpty()|| t_subject.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                user_teacher.updateEmail(t_email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        HashMap teacher=new HashMap();
                        teacher.put("email",t_email);
                        teacher.put("fullName",t_name);
                        teacher.put("subject",t_subject);

                        reference.child(userID).updateChildren(teacher);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed to Update",Toast.LENGTH_SHORT).show();
                    }
                });

                uploadImage(imageUri);

                Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //Upload Profile Picture to Firebase Storage
    private void uploadImage(Uri imageUri) {
        StorageReference fileRef=FirebaseStorage.getInstance().getReference().child("Teacher/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri);
    }


    //Display chosen file in ImageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1000)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                imageUri=data.getData();
                profile_Image.setImageURI(imageUri);
            }
        }

    }
}