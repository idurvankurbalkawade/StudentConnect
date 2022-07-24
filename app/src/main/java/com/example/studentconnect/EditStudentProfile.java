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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class EditStudentProfile extends AppCompatActivity {
    private EditText txt_stud_name,txt_stud_email,txt_stud_class;
    private Button saveBtn;
    private ImageView profile_Image;
    private FirebaseUser user_student;
    private DatabaseReference reference;
    private String userID;
    private FirebaseAuth mAuth;
    private StorageReference storageReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_edit_student_profile);

        txt_stud_name=(EditText) findViewById(R.id.txt_full_name);
        txt_stud_email=(EditText) findViewById(R.id.txt_email);
        txt_stud_class=(EditText) findViewById(R.id.txt_class);
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




        user_student= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Students");
        userID=user_student.getUid();

        StorageReference fileProfile=FirebaseStorage.getInstance().getReference().child("Student/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
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
                Student student=snapshot.getValue(Student.class);

                if(student!=null)
                {
                    String student_Name=student.studentName;
                    String student_email=student.email;
                    String student_class=student.studentClass;

                    //welcome_user.setText(welcome);
                    txt_stud_name.setText(student_Name);
                    txt_stud_email.setText(student_email);
                    txt_stud_class.setText(student_class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stud_name=txt_stud_name.getText().toString();
                String stud_email=txt_stud_email.getText().toString();
                String stud_class=txt_stud_class.getText().toString();

                updateStudentData(stud_name,stud_email,stud_class);

                uploadImage(imageUri);

                Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadImage(Uri imageUri) {
        StorageReference fileRef=FirebaseStorage.getInstance().getReference().child("Student/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri);
    }

    private void updateStudentData(String stud_name, String stud_email, String stud_class) {

        if(stud_name.isEmpty()|| stud_email.isEmpty()|| stud_class.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
            return;
        }

        user_student.updateEmail(stud_email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                HashMap student=new HashMap();
                student.put("studentName",stud_name);
                student.put("email",stud_email);
                student.put("studentClass",stud_class);

                reference.child(userID).updateChildren(student);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to update!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

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
