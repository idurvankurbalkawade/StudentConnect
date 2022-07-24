package com.example.studentconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Date;

public class Display extends AppCompatActivity {
    TextView e1,i1,n1,s1,t1,u1;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String et1;
    Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Bundle extras=getIntent().getExtras();
        Date currentTime = Calendar.getInstance().getTime();
        et1= String.valueOf(currentTime);
        e1=findViewById(R.id.e);
        i1=findViewById(R.id.i);
        s1=findViewById(R.id.s);
        t1=findViewById(R.id.t);
        u1=findViewById(R.id.u);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Submitted Assignments");




        String e=getIntent().getStringExtra("e");
        String i=getIntent().getStringExtra("i");
        String n=getIntent().getStringExtra("n");
        String s=getIntent().getStringExtra("s");
        String t=getIntent().getStringExtra("t");
        String u=getIntent().getStringExtra("u");

        e1.setText(e);
        i1.setText(i);
        s1.setText(s);
        t1.setText(t);
        u1.setText(u);

        Toast.makeText(getApplicationContext(), e+""+i+""+n+""+s+""+t+""+u, Toast.LENGTH_SHORT).show();


        u1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(u));
                startActivity(browserIntent);
            }
        });
        bt1=findViewById(R.id.bt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPDFfile();
            }
        });

    }

    private void SelectPDFfile() {
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select PDF FILE"),7);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==7 && resultCode == RESULT_OK
                && data!=null && data.getData() !=null){
            bt1.setEnabled(true);

            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadPDFFile(data.getData());
                }
            });
        }
    }

    private void uploadPDFFile(Uri data) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading.....");
        progressDialog.show();
        StorageReference reference =storageReference.child("Submitted Assignments/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Uri url=uri.getResult();
                        Student1 uploadpdf = new Student1(et1, url.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(uploadpdf);
                        Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress =(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Uploaded"+(int)progress+"%");
            }
        });




    }
}