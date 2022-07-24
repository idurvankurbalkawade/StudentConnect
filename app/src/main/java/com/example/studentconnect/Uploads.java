package com.example.studentconnect;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class Uploads extends AppCompatActivity {
    String title,instructions,from,to;
    TextView v1;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    String et1;
    Button bt1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploads);
        Date currentTime = Calendar.getInstance().getTime();

        et1= String.valueOf(currentTime);

        Bundle extras=getIntent().getExtras();
        v1=findViewById(R.id.v1);
        title=getIntent().getStringExtra("title");
        instructions=getIntent().getStringExtra("instructions");
        from=getIntent().getStringExtra("from");
        to=getIntent().getStringExtra("to");
        v1.setText("Start"+from+"\nend:"+to);

        Toast.makeText(getApplicationContext(), "Title:"+title+" instructions:"+instructions+"  from:"+from+ " to:"+to, Toast.LENGTH_SHORT).show();


        bt1=findViewById(R.id.Upload);

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("Assignment");


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
            uploadPDFFile(data.getData());
        }
    }

    private void uploadPDFFile(Uri data) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading.....");
        progressDialog.show();
        StorageReference reference =storageReference.child("uploads/"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        Uri url=uri.getResult();
                        Uploadpdf uploadpdf = new Uploadpdf(et1, url.toString(),title,instructions,from,to);
                        databaseReference.child(databaseReference.push().getKey()).setValue(uploadpdf);
                        Toast.makeText(getApplicationContext(), "File Uploaded and Assignment Assigned", Toast.LENGTH_SHORT).show();
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