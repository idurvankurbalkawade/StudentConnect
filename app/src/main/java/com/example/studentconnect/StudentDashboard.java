package com.example.studentconnect;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class StudentDashboard extends AppCompatActivity implements View.OnClickListener {
    CardView uploadAssign,Quiz,Study;
    //Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getSupportActionBar().setTitle("Student Dashboard");
        setContentView(R.layout.activity_student_dashboard);

        /*toolbar=(Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);*/

        uploadAssign=findViewById(R.id.assign);
        Quiz=(CardView) findViewById(R.id.quiz);
        Study=(CardView) findViewById(R.id.pdf);

        Quiz.setOnClickListener(this);
        Study.setOnClickListener(this);
        uploadAssign.setOnClickListener(this);




        //uploadAssign.setOnClickListener(new View.OnClickListener() {
        //  @Override
        //public void onClick(View view) {
        //  Intent intent;
        //intent=new Intent(MainActivity.this,assignment.class);
        //startActivity(intent);
        //    }
        // });


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
                startActivity(new Intent(StudentDashboard.this,StudentProfile.class));
                return true;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(StudentDashboard.this,LauncherPage.class);
                startActivity(intent);
                finish();
                finishAndRemoveTask();
                Toast.makeText(getApplicationContext(), "Logged Out Successfully!!!", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.quiz:
                startActivity(new Intent(StudentDashboard.this,QuizList.class));
                //finish();
                break;

            case R.id.pdf:
                startActivity(new Intent(StudentDashboard.this,ViewStudyMaterial.class));
                break;

            case R.id.assign:
                startActivity(new Intent(StudentDashboard.this,ViewAssignment.class));
                break;
        }
    }
}
