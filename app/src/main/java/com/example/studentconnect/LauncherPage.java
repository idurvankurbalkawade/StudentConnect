package com.example.studentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LauncherPage extends AppCompatActivity {
    Button teacher,student;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().hide();
        setContentView(R.layout.activity_launcher_page);

        teacher=(Button) findViewById(R.id.btn1);
        student=(Button) findViewById(R.id.btn2);
        text=(TextView)findViewById(R.id.t1);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LauncherPage.this,AdminLogin.class));
            }
        });



        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LauncherPage.this,TeacherLogin.class));
                finish();
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LauncherPage.this,StudentLogin.class));
                finish();
            }
        });
    }
}