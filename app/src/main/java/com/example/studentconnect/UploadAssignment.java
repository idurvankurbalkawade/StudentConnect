package com.example.studentconnect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


public class UploadAssignment extends AppCompatActivity {
    EditText Title, Instructions;

    Button Starts, Ends, Upload, assign;
    String title, instructions, from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload_assignment);



        Title = findViewById(R.id.edt2);
        Instructions = findViewById(R.id.edt1);

        Starts = findViewById(R.id.b1);
        Ends = findViewById(R.id.b2);
        Upload = findViewById(R.id.Upload);
        assign = findViewById(R.id.Assign);
        DAOAssignment dao= new DAOAssignment();
        setTitle("                     Assignment");

        Starts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Starts.class);

                startActivityForResult(intent, 2);

            }

        });
        Ends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Ends.class);

                startActivityForResult(intent, 3);

            }
        });

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title=Title.getText().toString();
                instructions=Instructions.getText().toString();

                Intent intent= new Intent(getApplicationContext(),Uploads.class);
                intent.putExtra("title",title);
                intent.putExtra("instructions",instructions);
                intent.putExtra("to",to);
                intent.putExtra("from",from);

                startActivity(intent);

            }
        });

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title=Title.getText().toString();
                instructions=Instructions.getText().toString();

                Assignment assi= new Assignment(title,instructions,from,to);

                dao.add(assi).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Record is inserted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {

            from = data.getStringExtra("current");
            Starts.setText(from);

            Toast.makeText(getApplicationContext(), "" + from, Toast.LENGTH_SHORT).show();
        }
        if (resultCode == 3) {

            to = data.getStringExtra("current");
            Ends.setText(to);


        }

    }
}