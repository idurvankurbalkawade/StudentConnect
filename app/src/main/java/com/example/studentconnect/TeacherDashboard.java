package com.example.studentconnect;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class TeacherDashboard extends AppCompatActivity implements View.OnClickListener {
    CardView uploadAssign,Quiz,study,viewAssign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().setTitle("Teacher Dashboard");
        setContentView(R.layout.activity_teacher_dashboard);

        Quiz=(CardView) findViewById(R.id.quiz);
        uploadAssign=findViewById(R.id.assign);
        study=(CardView) findViewById(R.id.pdf);
        viewAssign=(CardView) findViewById(R.id.ViewAssign);


        study.setOnClickListener(this);
        Quiz.setOnClickListener(this);
        uploadAssign.setOnClickListener(this);
        viewAssign.setOnClickListener(this);

    }

    //for menu


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.profile:
                //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TeacherDashboard.this,TeacherProfile.class));
                return true;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TeacherDashboard.this,LauncherPage.class));
                Toast.makeText(getApplicationContext(), "Logged Out Successfully!!!", Toast.LENGTH_SHORT).show();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.pdf:
                startActivity(new Intent(TeacherDashboard.this,UploadStudyMaterial.class));
                break;
            case R.id.quiz:
                startActivity(new Intent(TeacherDashboard.this,AddQuestion.class));
                break;

            case R.id.assign:
                startActivity(new Intent(TeacherDashboard.this,UploadAssignment.class));
                break;

            case R.id.ViewAssign:
                startActivity(new Intent(TeacherDashboard.this,SubmittedAssignment.class));
                break;
        }
    }
}