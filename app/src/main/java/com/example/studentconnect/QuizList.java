package com.example.studentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class QuizList extends AppCompatActivity {
    String QuizList[]={"Quiz1","Quiz2","Quiz3"};
    ListView l1;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);
        l1=(ListView) findViewById(R.id.qlist);
        backBtn=(Button) findViewById(R.id.backBtn);


        ArrayAdapter ad=new ArrayAdapter(this,android.R.layout.simple_list_item_1,QuizList);
        l1.setAdapter(ad);

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "position is "+i, Toast.LENGTH_SHORT).show();
                if(i==0) {
                    Intent intent=new Intent(getApplicationContext(),QuizActivity.class);
                    startActivity(intent);
                }
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