package com.example.studentconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Ends extends AppCompatActivity {
    TimePicker t1;
    Button bt1,Submit;
    String current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ends); t1=findViewById(R.id.t1);
        bt1=findViewById(R.id.bt1);
        Submit=findViewById(R.id.Submit);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current=getCurrentTime1();
                Toast.makeText(getApplicationContext(), ""+current, Toast.LENGTH_SHORT).show();
                bt1.setText(current);


            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =getIntent();

                intent.putExtra("current",current);


                setResult(3,intent);
                finish();


            }
        });
    }

    private String getCurrentTime1() {
        String currentTime=t1.getCurrentHour()+":"+t1.getCurrentMinute();
        return currentTime;
    }
}
