package com.example.studentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WonActivity extends AppCompatActivity {
    ProgressBar p1;
    TextView res,show;
    int correct=0,wrong=0;
    int total=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);
        p1=(ProgressBar) findViewById(R.id.resultbar);
        res=(TextView) findViewById(R.id.score);
        correct=getIntent().getIntExtra("correct",0);
        wrong=getIntent().getIntExtra("wrong",0);
        show=(TextView)findViewById(R.id.showr);
        total=correct+wrong;
        p1.setMax(total);
        p1.setProgress(correct);
        res.setText(new StringBuilder().append(correct).append("/").append(total).toString());
        show.setText("Your score is "+correct);
    }

    public void back(View view){
        Intent intent=new Intent(WonActivity.this,QuizList.class);
        startActivity(intent);
        //finishAndRemoveTask();
    }

}
